package com.szoldapps.weli.writer.presentation.round

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue.RoundHeader
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue.RoundValue
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.round.RoundViewState.Content
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import kotlin.random.Random

class RoundViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roundId: Long =
        savedStateHandle.get<Long>("roundId") ?: throw kotlin.IllegalStateException("Mandatory roundId is missing!")

    val viewState: LiveData<RoundViewState> =
        Transformations.map(weliRepository.roundValuesByRoundId(roundId)) { roundValues ->
            Content(
                listOf(RoundHeader(listOf("AK", "TM", "TE", "TS"))) + roundValues
            )
        }

    fun addRandomRoundValues() = viewModelScope.launch {
        val playersOfRound = weliRepository.getPlayersOfRound(roundId)
        playersOfRound.forEach { player ->
            weliRepository.addRoundValue(
                RoundValue(
                    date = OffsetDateTime.now(),
                    number = Random.nextInt(0, 25),
                    value = Random.nextInt(0, 25),
                ),
                roundId,
                player
            )
        }

    }
}

sealed class RoundViewState {
    object Loading : RoundViewState()
    object Error : RoundViewState()
    data class Content(val rounds: List<RoundRvAdapterValue>) : RoundViewState()
}
