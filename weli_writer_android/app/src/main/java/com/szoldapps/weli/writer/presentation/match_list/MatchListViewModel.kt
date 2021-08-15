package com.szoldapps.weli.writer.presentation.match_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val weliRepository: WeliRepository
) : ViewModel() {

    val viewState: LiveData<MatchViewState> = Transformations.map(weliRepository.matches) { matches ->
        Content(matches)
    }

    fun addRandomMatch() = viewModelScope.launch {
        weliRepository.addMatch(Match(date = OffsetDateTime.now(), location = "testLocation"))
    }
}

sealed class MatchViewState {
    object Loading : MatchViewState()
    object Error : MatchViewState()
    data class Content(val matches: List<Match>) : MatchViewState()
}
