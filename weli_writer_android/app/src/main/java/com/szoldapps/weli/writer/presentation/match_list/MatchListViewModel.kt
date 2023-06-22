package com.szoldapps.weli.writer.presentation.match_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.MatchRepository
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
) : ViewModel() {

    val uiState: StateFlow<MatchViewState> =
        matchRepository.matches
            .map { matches -> Content(matches) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MatchViewState.Loading,
            )

    fun addRandomMatch() = viewModelScope.launch {
        matchRepository.addMatch(Match(date = OffsetDateTime.now(), location = "testLocation"))
    }
}

sealed class MatchViewState {
    object Loading : MatchViewState()
    object Error : MatchViewState()
    data class Content(val matches: List<Match>) : MatchViewState()
}
