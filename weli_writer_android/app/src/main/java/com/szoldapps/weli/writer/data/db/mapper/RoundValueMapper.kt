package com.szoldapps.weli.writer.data.db.mapper

import com.szoldapps.weli.writer.data.db.entity.RoundValueEntity
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue.RoundRowValues
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue.RoundValue

fun List<RoundValueEntity>.mapToRoundRowValues(): List<RoundRowValues> {
    val roundRowValues = mutableListOf<RoundRowValues>()
    val numberToRoundValueEntityMap = this.groupBy { it.number }
    numberToRoundValueEntityMap.keys.forEach { number ->
        val roundValueEntities = numberToRoundValueEntityMap[number] ?: throw IllegalStateException("Number not found!")
        roundRowValues.add(
            RoundRowValues(
                number = number,
                values = roundValueEntities.map { it.value }
            )
        )
    }
    return roundRowValues
}

fun RoundValue.mapToRoundValueEntity(roundId: Long, playerId: Long): RoundValueEntity =
    RoundValueEntity(
        dateTime = date,
        number = number,
        value = value,
        roundId = roundId,
        playerId = playerId
    )
