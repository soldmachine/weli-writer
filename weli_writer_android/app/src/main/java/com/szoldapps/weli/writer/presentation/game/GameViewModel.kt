package com.szoldapps.weli.writer.presentation.match

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match.MatchViewState.Content
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class GameViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository
) : ViewModel() {

    val viewState: LiveData<MatchViewState> = Transformations.map(weliRepository.matches) { matches ->
        Content(matches)
    }

    fun addRandomMatch() = viewModelScope.launch {
        weliRepository.addMatch(Match(date = OffsetDateTime.now(), location = "testLocation"))
    }

}

sealed class GameViewState {
    object Loading : GameViewState()
    object Error : GameViewState()
    data class Content(val matches: List<Match>) : GameViewState()
}
