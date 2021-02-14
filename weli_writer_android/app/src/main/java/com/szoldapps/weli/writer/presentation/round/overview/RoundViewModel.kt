package com.szoldapps.weli.writer.presentation.round.overview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowButton
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowHeader
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowValues
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.helper.SingleLiveEvent
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewEvent.OpenBottomSheet
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewState.Content

internal class RoundViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roundId: Long =
        savedStateHandle.get<Long>("roundId") ?: throw kotlin.IllegalStateException("Mandatory roundId is missing!")

    val viewState: LiveData<RoundViewState> =
        Transformations.map(weliRepository.roundRowValuesByRoundId(roundId)) { roundRowValues ->
            val list = mutableListOf<RoundValueRvAdapterItem>(RoundRowHeader(listOf("AK", "TM", "TE", "TS")))
            list.addAll(roundRowValues)
            if (roundRowValues.doNotContainWinner()) {
                list.add(RoundRowButton(label = "Add round result", action = { _viewEvent.postValue(OpenBottomSheet) }))
            }
            Content(list.toList())
        }

    private val _viewEvent = SingleLiveEvent<RoundViewEvent>()
    val viewEvent: LiveData<RoundViewEvent> = _viewEvent

    private fun List<RoundRowValues>.doNotContainWinner(): Boolean = !last().values.contains(0)
}

internal sealed class RoundViewState {
    object Loading : RoundViewState()
    object Error : RoundViewState()
    data class Content(val rounds: List<RoundValueRvAdapterItem>) : RoundViewState()
}

internal sealed class RoundViewEvent {
    object OpenBottomSheet : RoundViewEvent()
}
