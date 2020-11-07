package com.szoldapps.weli.writer.presentation.match

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match.MatchViewState.Content
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class MatchViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val matchId: Int =
        savedStateHandle.get<Int>("matchId") ?: throw kotlin.IllegalStateException("Mandatory matchId is missing!")

    val viewState: LiveData<MatchViewState> = Transformations.map(weliRepository.gamesByMatchId(matchId)) { games ->
        Content(games)
    }

    fun addRandomGame() = viewModelScope.launch {
        weliRepository.addGame(Game(date = OffsetDateTime.now()), matchId)
    }
}

sealed class MatchViewState {
    object Loading : MatchViewState()
    object Error : MatchViewState()
    data class Content(val games: List<Game>) : MatchViewState()
}
