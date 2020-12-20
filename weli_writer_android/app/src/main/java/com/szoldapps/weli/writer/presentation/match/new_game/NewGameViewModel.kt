package com.szoldapps.weli.writer.presentation.match.new_game

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.szoldapps.weli.writer.domain.Player
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.Content

class NewGameViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val matchId: Long =
        savedStateHandle.get<Long>("matchId") ?: throw kotlin.IllegalStateException("Mandatory matchId is missing!")

    val viewState: LiveData<NewGameViewState> = Transformations.map(weliRepository.gamesByMatchId(matchId)) { games ->
        Content(emptyList())
    }
}

sealed class NewGameViewState {
    object Loading : NewGameViewState()
    object Error : NewGameViewState()
    data class Content(val players: List<Player>) : NewGameViewState()
}
