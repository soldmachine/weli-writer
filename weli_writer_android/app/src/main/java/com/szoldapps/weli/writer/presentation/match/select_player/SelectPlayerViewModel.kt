package com.szoldapps.weli.writer.presentation.match.select_player

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Player
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match.select_player.SelectPlayerViewState.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPlayerViewModel @Inject constructor(
    private val weliRepository: WeliRepository,
) : ViewModel() {

    val viewState: LiveData<SelectPlayerViewState> = weliRepository.players.map { players ->
        Content(players)
    }

    fun addPlayer(firstName: String, lastName: String) = viewModelScope.launch {
        weliRepository.addPlayer(
            Player(firstName = firstName, lastName = lastName)
        )
    }
}

sealed class SelectPlayerViewState {
    object Loading : SelectPlayerViewState()
    object Error : SelectPlayerViewState()
    data class Content(val players: List<Player>) : SelectPlayerViewState()
}
