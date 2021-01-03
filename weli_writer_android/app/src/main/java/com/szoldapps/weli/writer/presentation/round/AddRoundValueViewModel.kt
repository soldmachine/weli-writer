package com.szoldapps.weli.writer.presentation.round

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundValue
import com.szoldapps.weli.writer.domain.WeliRepository
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

internal class AddRoundValueViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roundId: Long =
        savedStateHandle.get<Long>("roundId") ?: throw IllegalStateException("Mandatory roundId is missing!")

    private val _values: MutableList<Int> = mutableListOf(0, 0, 0, 0)

    fun updateValue(index: Int, value: Int) {
        _values.add(index, value)
    }

    fun addRoundValue() = viewModelScope.launch {
        val playersOfRound = weliRepository.getPlayersOfRound(roundId)
        val number = weliRepository.roundValueCountByRoundId(roundId)
        playersOfRound.forEachIndexed { index, player ->
            weliRepository.addRoundValue(
                RoundValue(
                    date = OffsetDateTime.now(),
                    number = number,
                    value = _values[index],
                ),
                roundId,
                player
            )
        }
    }

}
