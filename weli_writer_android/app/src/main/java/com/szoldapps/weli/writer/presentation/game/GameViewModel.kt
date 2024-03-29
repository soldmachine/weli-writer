package com.szoldapps.weli.writer.presentation.game

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.GameRvAdapterItem
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.RoundValue
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.WeliConstants.WELI_ROUND_START_VALUE
import com.szoldapps.weli.writer.presentation.common.helper.SingleLiveEvent
import com.szoldapps.weli.writer.presentation.game.GameViewState.Content
import com.szoldapps.weli.writer.presentation.game.GameViewState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val weliRepository: WeliRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val gameId: Long =
        savedStateHandle.get<Long>("gameId") ?: throw kotlin.IllegalStateException("Mandatory gameId is missing!")

    private val _viewState = MutableLiveData<GameViewState>()
    val viewState: LiveData<GameViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<GameViewEvent>()
    val viewEvent: LiveData<GameViewEvent> = _viewEvent

    fun loadContent(gameId: Long) = viewModelScope.launch {
        _viewState.postValue(Loading)
        val rvAdapterItems = weliRepository.gameRvAdapterItemsByGameId(gameId)
        _viewState.postValue(Content(rvAdapterItems))
    }

    fun addRandomRound() = viewModelScope.launch {
        val roundId = weliRepository.addRound(Round(date = OffsetDateTime.now()), gameId)
        val players = weliRepository.getPlayersOfRound(roundId)
        players.forEach { player ->
            weliRepository.addRoundValue(
                RoundValue(
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
    data class Content(val rvItems: List<GameRvAdapterItem>) : GameViewState()
}

sealed class GameViewEvent {
    data class OpenRoundFragment(val roundId: Long) : GameViewEvent()
}
