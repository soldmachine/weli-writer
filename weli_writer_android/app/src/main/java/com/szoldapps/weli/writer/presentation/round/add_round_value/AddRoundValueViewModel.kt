package com.szoldapps.weli.writer.presentation.round.add_round_value

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.RoundValue
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
            tricks = listOf(0, 0, 0, 0),
            isAddValuesButtonEnabled = false,
        )
    )
    val viewState: LiveData<AddRoundValueViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<AddRoundValueViewEvent>()
    val viewEvent: LiveData<AddRoundValueViewEvent> = _viewEvent

    fun updateTrick(index: Int, value: Int) {
        val content = _viewState.value as Content
        val tricks = content.tricks.toMutableList().apply {
            set(index, value)
        }
        _viewState.value = content.copy(
            tricks = tricks,
            isAddValuesButtonEnabled = isAddValuesButtonEnabled(tricks)
        )
    }

    private fun isAddValuesButtonEnabled(tricks: List<Int>): Boolean {
        val fiveTricksDistributed = tricks.filter { it < 0 }.sum() == -5
        val noUnchangedPlayers = tricks.count { it == 0 } == 0
        return fiveTricksDistributed && noUnchangedPlayers
    }

    fun addRoundValue() = viewModelScope.launch {
        val playersOfRound = weliRepository.getPlayersOfRound(roundId)
        val number = weliRepository.roundValueCountByRoundId(roundId)
        playersOfRound.forEachIndexed { index, player ->
            val content = _viewState.value as Content
            weliRepository.addRoundValue(
                RoundValue(
                    date = OffsetDateTime.now(),
                    number = number,
                    value = content.tricks[index] * content.multiplier,
                ),
                roundId,
                player
            )
        }
        _viewEvent.value = CloseFragment
    }

    fun updateHeartsRound(isChecked: Boolean) {
        refreshValuesWithNewMultiplier(heartsMultiplierArg = heartsMultiplier(isChecked))
    }

    private fun refreshValuesWithNewMultiplier(heartsMultiplierArg: Int? = null, redealMultiplierArg: Int? = null) {
        val content = _viewState.value as Content
        val heartsMultiplier = heartsMultiplierArg ?: content.heartsMultiplier
        val redealMultiplier = redealMultiplierArg ?: content.redealMultiplier
        val multiplier = heartsMultiplier * redealMultiplier
        _viewState.value = content.copy(
            heartsMultiplier = heartsMultiplier,
            redealMultiplier = redealMultiplier,
            multiplier = multiplier,
        )
    }

    private fun heartsMultiplier(isHeartsRound: Boolean) = if (isHeartsRound) 2 else 1

    fun updateRedealState() {
        val content = _viewState.value as Content
        val newRedealMultiplier = content.redealMultiplier * 2
        val redealMultiplier = if (newRedealMultiplier > MAX_REDEAL_MULTIPLIER) 1 else newRedealMultiplier
        refreshValuesWithNewMultiplier(redealMultiplierArg = redealMultiplier)
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
        val tricks: List<Int>,
        val isAddValuesButtonEnabled: Boolean,
    ) : AddRoundValueViewState()
}

internal sealed class AddRoundValueViewEvent {
    object CloseFragment : AddRoundValueViewEvent()
}
