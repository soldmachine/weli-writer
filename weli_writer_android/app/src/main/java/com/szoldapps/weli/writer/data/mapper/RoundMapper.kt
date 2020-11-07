package com.szoldapps.weli.writer.data.mapper

import com.szoldapps.weli.writer.data.entity.RoundEntity
import com.szoldapps.weli.writer.domain.Round

fun RoundEntity.mapToRound(): Round =
    Round(
        id = roundId,
        date = dateTime,
    )

fun List<RoundEntity>.mapToRounds(): List<Round> = this.map { gameEntity -> gameEntity.mapToRound() }

fun Round.mapToRoundEntity(gameId: Int): RoundEntity =
    RoundEntity(
        dateTime = date,
        gameId = gameId
    )
