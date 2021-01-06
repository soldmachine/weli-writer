package com.szoldapps.weli.writer.presentation.round.add_round_value

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundValue
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.helper.SingleLiveEvent
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewEvent.CloseFragment
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewEvent
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

internal class AddRoundValueViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roundId: Long =
        savedStateHandle.get<Long>("roundId") ?: throw IllegalStateException("Mandatory roundId is missing!")

    private val _values = MutableLiveData(listOf(0, 0, 0, 0))
    val values: LiveData<List<Int>> = _values

    private val _viewEvent = SingleLiveEvent<AddRoundValueViewEvent>()
    val viewEvent: LiveData<AddRoundValueViewEvent> = _viewEvent

    fun updateValue(index: Int, value: Int) {
        val list = _values.value ?: throw IllegalStateException("")
        _values.value = list.toMutableList().apply {
            set(index, value)
        }
    }

    fun addRoundValue() = viewModelScope.launch {
        val playersOfRound = weliRepository.getPlayersOfRound(roundId)
        val number = weliRepository.roundValueCountByRoundId(roundId)
        playersOfRound.forEachIndexed { index, player ->
            weliRepository.addRoundValue(
                RoundValue(
                    date = OffsetDateTime.now(),
                    number = number,
                    value = _values.value?.get(index) ?: 0,
                ),
                roundId,
                player
            )
        }
        _viewEvent.value = CloseFragment
    }

}

internal sealed class AddRoundValueViewEvent {
    object CloseFragment : AddRoundValueViewEvent()
}
