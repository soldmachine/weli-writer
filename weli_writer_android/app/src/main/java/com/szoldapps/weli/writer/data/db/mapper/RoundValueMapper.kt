package com.szoldapps.weli.writer.data.db.mapper

import com.szoldapps.weli.writer.data.db.entity.RoundValueEntity
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowValues
import com.szoldapps.weli.writer.domain.RoundValue

fun List<RoundValueEntity>.mapToRoundRowValues(): List<RoundRowValues> {
    val roundRowValues = mutableListOf<RoundRowValues>()
    val numberToRoundValueEntityMap = this.groupBy { it.number }
    var sumOfPreviousRounds = emptyList<Int>()
    numberToRoundValueEntityMap.keys.forEach { number ->
        val roundValueEntities = numberToRoundValueEntityMap[number] ?: throw IllegalStateException("Number not found!")
        sumOfPreviousRounds = roundValueEntities.map { it.value }.sumValuesWith(sumOfPreviousRounds)
        roundRowValues.add(
            RoundRowValues(
                number = number,
                values = sumOfPreviousRounds
            )
        )
    }
    return roundRowValues
}

private fun List<Int>.sumValuesWith(bList: List<Int>): List<Int> =
    this.mapIndexed { index, aListItem ->
        aListItem + (bList.getOrNull(index) ?: 0)
    }

fun RoundValue.mapToRoundValueEntity(roundId: Long, playerId: Long): RoundValueEntity =
    RoundValueEntity(
        dateTime = date,
        number = number,
        value = value,
        roundId = roundId,
        playerId = playerId
    )
