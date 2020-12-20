package com.szoldapps.weli.writer.presentation.game

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.game.GameViewState.Content
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewEvent
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class GameViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val gameId: Long =
        savedStateHandle.get<Long>("gameId") ?: throw kotlin.IllegalStateException("Mandatory gameId is missing!")

    val viewState: LiveData<GameViewState> = Transformations.map(weliRepository.roundsByGameId(gameId)) { rounds ->
        Content(rounds)
    }

    private val _viewEvent = MutableLiveData<GameViewEvent>()
    val viewEvent: LiveData<GameViewEvent> = _viewEvent

    fun addRandomRound() = viewModelScope.launch {
        val roundId = weliRepository.addRound(Round(date = OffsetDateTime.now()), gameId)
        _viewEvent.value = GameViewEvent.OpenRoundFragment(roundId)
    }

}

sealed class GameViewState {
    object Loading : GameViewState()
    object Error : GameViewState()
    data class Content(val rounds: List<Round>) : GameViewState()
}

sealed class GameViewEvent {
    data class OpenRoundFragment(val roundId: Long) : GameViewEvent()
}
