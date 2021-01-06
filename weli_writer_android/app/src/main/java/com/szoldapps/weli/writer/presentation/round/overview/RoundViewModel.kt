package com.szoldapps.weli.writer.presentation.round

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.*
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.round.RoundViewEvent.OpenBottomSheet
import com.szoldapps.weli.writer.presentation.round.RoundViewState.Content
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import kotlin.random.Random

internal class RoundViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roundId: Long =
        savedStateHandle.get<Long>("roundId") ?: throw kotlin.IllegalStateException("Mandatory roundId is missing!")

    val viewState: LiveData<RoundViewState> =
        Transformations.map(weliRepository.roundRowValuesByRoundId(roundId)) { roundRowValues ->
            Content(
                listOf(RoundRowHeader(listOf("AK", "TM", "TE", "TS")))
                        + roundRowValues
                        + RoundRowButton(label = "Add round result", action = { _viewEvent.value = OpenBottomSheet })
            )
        }

    private val _viewEvent = MutableLiveData<RoundViewEvent>()
    val viewEvent: LiveData<RoundViewEvent> = _viewEvent

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

internal sealed class RoundViewState {
    object Loading : RoundViewState()
    object Error : RoundViewState()
    data class Content(val rounds: List<RoundValueRvAdapterItem>) : RoundViewState()
}

internal sealed class RoundViewEvent {
    object OpenBottomSheet : RoundViewEvent()
}
