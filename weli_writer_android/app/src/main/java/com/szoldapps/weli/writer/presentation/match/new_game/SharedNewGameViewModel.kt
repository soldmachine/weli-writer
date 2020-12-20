package com.szoldapps.weli.writer.presentation.match.new_game

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szoldapps.weli.writer.domain.Player

class SharedNewGameViewModel @ViewModelInject constructor(
) : ViewModel() {

    private val _selectedPlayers = MutableLiveData<List<Player?>>(listOf(null, null, null, null))
    val selectedPlayers: LiveData<List<Player?>> = _selectedPlayers

    fun selectPlayer(index: Int, player: Player) {
        val players = _selectedPlayers.value?.toMutableList()
        players?.set(index, player)
        _selectedPlayers.value = players
    }
}
