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
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Content
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

internal class AddRoundValueViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roundId: Long =
        savedStateHandle.get<Long>("roundId") ?: throw IllegalStateException("Mandatory roundId is missing!")

    private val _viewState = MutableLiveData<AddRoundValueViewState>(
        Content(
            heartsMultiplier = 1,
            redealMultiplier = 1,
            multiplier = 1,
            values = listOf(0, 0, 0, 0),
        )
    )
    val viewState: LiveData<AddRoundValueViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<AddRoundValueViewEvent>()
    val viewEvent: LiveData<AddRoundValueViewEvent> = _viewEvent

    private var isHeartsRound = false
    private var redealMultiplier = 1

    fun updateValue(index: Int, value: Int) {
        val content = _viewState.value as Content
        _viewState.value = content.copy(
            values = content.values.toMutableList().apply {
                set(index, value)
            }
        )
    }

    fun addRoundValue() = viewModelScope.launch {
        val playersOfRound = weliRepository.getPlayersOfRound(roundId)
        val number = weliRepository.roundValueCountByRoundId(roundId)
        playersOfRound.forEachIndexed { index, player ->
            weliRepository.addRoundValue(
                RoundValue(
                    date = OffsetDateTime.now(),
                    number = number,
                    value = (_viewState.value as Content).values[index],
                ),
                roundId,
                player
            )
        }
        _viewEvent.value = CloseFragment
    }

    fun updateHeartsRound(isChecked: Boolean) {
        isHeartsRound = isChecked
        refreshValuesWithNewMultiplier()
    }

    private fun refreshValuesWithNewMultiplier() {
        val content = _viewState.value as Content
        val multiplier = getMultiplier()
        _viewState.value = content.copy(
            heartsMultiplier = heartsMultiplier(),
            redealMultiplier = redealMultiplier,
            multiplier = multiplier,
        )
    }

    private fun getMultiplier(): Int = heartsMultiplier() * redealMultiplier

    private fun heartsMultiplier() = if (isHeartsRound) 2 else 1

    fun updateRedealState() {
        redealMultiplier *= 2
        if (redealMultiplier > MAX_REDEAL_MULTIPLIER) {
            redealMultiplier = 1
        }
        refreshValuesWithNewMultiplier()
    }

    companion object {
        private const val MAX_REDEAL_MULTIPLIER = 8
    }

}

internal sealed class AddRoundValueViewState {
    object Loading : AddRoundValueViewState()
    object Error : AddRoundValueViewState()
    data class Content(
        val heartsMultiplier: Int,
        val redealMultiplier: Int,
        val multiplier: Int,
        val values: List<Int>,
    ) : AddRoundValueViewState()
}

internal sealed class AddRoundValueViewEvent {
    object CloseFragment : AddRoundValueViewEvent()
}
