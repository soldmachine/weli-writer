package com.szoldapps.weli.writer.presentation.match.new_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Player
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.helper.SingleLiveEvent
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewEvent.OpenGameFragment
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.Content
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val weliRepository: WeliRepository,
) : ViewModel() {

    private val _selectedPlayers: MutableList<Player?> = mutableListOf(null, null, null, null)

    private val _viewState = MutableLiveData<NewGameViewState>(Content(_selectedPlayers))
    val viewState: LiveData<NewGameViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<NewGameViewEvent>()
    val viewEvent: LiveData<NewGameViewEvent> = _viewEvent

    fun getSelectedPlayers() = _selectedPlayers.filterNotNull()

    fun selectPlayer(index: Int, player: Player) {
        _selectedPlayers[index] = player
        _viewState.value = Content(_selectedPlayers)
    }

    fun createGame(matchId: Long) = viewModelScope.launch {
        _viewState.value = Loading
        val gameId = weliRepository.addGame(
            game = Game(
                date = OffsetDateTime.now(),
                players = _selectedPlayers.filterNotNull()
            ),
            matchId = matchId
        )
        _viewEvent.value = OpenGameFragment(gameId)
    }

    fun resetSelectedPlayers() {
        _selectedPlayers.forEachIndexed { index, _ -> _selectedPlayers[index] = null }
        _viewState.value = Content(_selectedPlayers)
    }
}

sealed class NewGameViewState {
    object Loading : NewGameViewState()
    object Error : NewGameViewState()
    data class Content(val players: List<Player?>) : NewGameViewState()
}

sealed class NewGameViewEvent {
    data class OpenGameFragment(val gameId: Long) : NewGameViewEvent()
}
