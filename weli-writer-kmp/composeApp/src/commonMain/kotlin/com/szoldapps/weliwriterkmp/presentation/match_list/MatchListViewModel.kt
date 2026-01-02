package com.szoldapps.weliwriterkmp.presentation.match_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weliwriterkmp.domain.Match
import com.szoldapps.weliwriterkmp.domain.MatchRepository
import com.szoldapps.weliwriterkmp.presentation.match_list.MatchListUiState.Content
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class MatchListViewModel(
    private val matchRepository: MatchRepository,
) : ViewModel() {

    val uiState: StateFlow<MatchListUiState> =
        matchRepository.matches
            .map { matches -> Content(matches) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MatchListUiState.Loading,
            )

    fun addRandomMatch() = viewModelScope.launch {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        matchRepository.addMatch(Match(date = now, location = "testLocation"))
    }
}

sealed class MatchListUiState {
    object Loading : MatchListUiState()
    object Error : MatchListUiState()
    data class Content(val matches: List<Match>) : MatchListUiState()
}
