package com.szoldapps.weli.writer.presentation.game

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.game.GameViewState.Content
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class GameViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val gameId: Int =
        savedStateHandle.get<Int>("gameId") ?: throw kotlin.IllegalStateException("Mandatory gameId is missing!")

    val viewState: LiveData<GameViewState> = Transformations.map(weliRepository.roundsByGameId(gameId)) { rounds ->
        Content(rounds)
    }

    fun addRandomRound() = viewModelScope.launch {
        weliRepository.addRound(Round(date = OffsetDateTime.now()), gameId)
    }

}

sealed class GameViewState {
    object Loading : GameViewState()
    object Error : GameViewState()
    data class Content(val rounds: List<Round>) : GameViewState()
}
