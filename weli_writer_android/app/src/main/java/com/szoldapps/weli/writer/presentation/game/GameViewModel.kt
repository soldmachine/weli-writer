package com.szoldapps.weli.writer.presentation.game

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.WeliConstants.WELI_ROUND_START_VALUE
import com.szoldapps.weli.writer.presentation.common.helper.SingleLiveEvent
import com.szoldapps.weli.writer.presentation.game.GameViewState.Content
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

    private val _viewEvent = SingleLiveEvent<GameViewEvent>()
    val viewEvent: LiveData<GameViewEvent> = _viewEvent

    fun addRandomRound() = viewModelScope.launch {
        val roundId = weliRepository.addRound(Round(date = OffsetDateTime.now()), gameId)
        val players = weliRepository.getPlayersOfRound(roundId)
        players.forEach { player ->
            weliRepository.addRoundValue(
                RoundValueRvAdapterItem.RoundValue(
                    date = OffsetDateTime.now(),
                    number = 0,
                    value = WELI_ROUND_START_VALUE,
                ),
                roundId,
                player
            )
        }
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
