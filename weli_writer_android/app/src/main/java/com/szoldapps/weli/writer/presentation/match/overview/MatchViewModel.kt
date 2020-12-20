package com.szoldapps.weli.writer.presentation.match.overview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.match.overview.MatchViewState.Content

class MatchViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val matchId: Long =
        savedStateHandle.get<Long>("matchId") ?: throw kotlin.IllegalStateException("Mandatory matchId is missing!")

    val viewState: LiveData<MatchViewState> = Transformations.map(weliRepository.gamesByMatchId(matchId)) { games ->
        Content(games)
    }
}

sealed class MatchViewState {
    object Loading : MatchViewState()
    object Error : MatchViewState()
    data class Content(val games: List<Game>) : MatchViewState()
}
