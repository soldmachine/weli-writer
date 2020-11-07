package com.szoldapps.weli.writer.data.mapper

import com.szoldapps.weli.writer.data.entity.RoundValueEntity
import com.szoldapps.weli.writer.domain.RoundValue

fun RoundValueEntity.mapToRoundValue(): RoundValue =
    RoundValue(
        id = roundValueId,
        date = dateTime,
        number = number,
        value = value
    )

fun List<RoundValueEntity>.mapToRoundValues(): List<RoundValue> =
    this.map { roundValueEntity -> roundValueEntity.mapToRoundValue() }

fun RoundValue.mapToRoundValueEntity(roundId: Long): RoundValueEntity =
    RoundValueEntity(
        dateTime = date,
        number = number,
        value = value,
        roundId = roundId
    )
