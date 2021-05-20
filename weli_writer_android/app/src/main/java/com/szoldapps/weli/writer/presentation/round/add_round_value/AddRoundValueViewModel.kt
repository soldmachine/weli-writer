package com.szoldapps.weli.writer.presentation.round.add_round_value

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
) : ViewModel() {

    private val _viewState = MutableLiveData<AddRoundValueViewState>(
        Content(
            heartsMultiplier = 1,
            redealMultiplier = 1,
            multiplier = 1,
            playerInitials = listOf("-", "-", "-", "-"),
            tricks = listOf(0, 0, 0, 0),
            isAddValuesButtonEnabled = false,
            isMulaRound = false,
            isEditMode = false,
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
            isAddValuesButtonEnabled = isAddValuesButtonEnabled(content.isMulaRound, tricks)
        )
    }

    private fun isAddValuesButtonEnabled(isMulaRound: Boolean, tricks: List<Int>): Boolean {
        if (isMulaRound) {
            // success case (winner && playerCount-1 losers)
            if (tricks.count { it == MULA_SUCCESS_POINTS } == 1 &&
                tricks.count { it == MULA_FAILURE_POINTS } == tricks.size - 1) {
                return true
            }
            // failure case (1 loser && 1 winner && playerCount-1 neutral)
            if (tricks.count { it == MULA_FAILURE_POINTS } == 1 &&
                tricks.count { it == MULA_HELD_POINTS } == 1 &&
                tricks.count { it == MULA_STAY_POINTS } == tricks.size - 2) {
                return true
            }
            return false
        } else {
            val selfFaller = tricks.count { it == SELF_FALLEN_POINTS } == 1
            val fiveTricksDistributed = tricks.filter { it < 0 }.sum() == -5
            val noUnchangedPlayers = tricks.count { it == 0 } == 0
            return (fiveTricksDistributed || selfFaller) && noUnchangedPlayers
        }
    }

    fun addRoundValue(roundId: Long, roundNumber: Int) = viewModelScope.launch {
        val content = _viewState.value as Content
        if (content.isEditMode) {
            weliRepository.updateRoundValues(roundId, roundNumber, content.tricks.map { it * content.multiplier })
        } else {
            val playersOfRound = weliRepository.getPlayersOfRound(roundId)
            val number = weliRepository.roundValueCountByRoundId(roundId)
            playersOfRound.forEachIndexed { index, player ->
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

    fun loadPlayerInitials(roundId: Long, roundNumber: Int) = viewModelScope.launch {
        val playerInitials = weliRepository.getPlayerInitialsOfRound(roundId)
        val content = if (roundNumber != -1) {
            val tricks = weliRepository.getTricksByRoundIdAndNumber(roundId, roundNumber)
            (_viewState.value as Content).copy(
                playerInitials = playerInitials,
                tricks = tricks,
                isEditMode = true,
            )
        } else {
            (_viewState.value as Content).copy(
                playerInitials = playerInitials
            )
        }
        _viewState.postValue(content)
    }

    fun updateMulaRound(isChecked: Boolean) {
        val content = (_viewState.value as Content).copy(
            isMulaRound = isChecked
        )
        _viewState.postValue(content)
    }

    companion object {
        private const val MAX_REDEAL_MULTIPLIER = 8
        private const val MULA_SUCCESS_POINTS = -20
        private const val MULA_FAILURE_POINTS = 20
        private const val MULA_HELD_POINTS = -1
        private const val MULA_STAY_POINTS = 0
        private const val SELF_FALLEN_POINTS = 10
    }

}

internal sealed class AddRoundValueViewState {
    object Loading : AddRoundValueViewState()
    object Error : AddRoundValueViewState()
    data class Content(
        val heartsMultiplier: Int,
        val redealMultiplier: Int,
        val multiplier: Int,
        val playerInitials: List<String>,
        val tricks: List<Int>,
        val isAddValuesButtonEnabled: Boolean,
        val isMulaRound: Boolean,
        val isEditMode: Boolean,
    ) : AddRoundValueViewState()
}

internal sealed class AddRoundValueViewEvent {
    object CloseFragment : AddRoundValueViewEvent()
}
