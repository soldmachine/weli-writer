package com.szoldapps.weliwriterkmp.data.db.mapper

import com.szoldapps.weliwriterkmp.data.db.entity.RoundEntity
import com.szoldapps.weliwriterkmp.domain.Round

fun RoundEntity.mapToRound(): Round =
    Round(
        id = roundId,
        date = dateTime,
    )

fun List<RoundEntity>.mapToRounds(): List<Round> = this.map { gameEntity -> gameEntity.mapToRound() }

fun Round.mapToRoundEntity(gameId: Long): RoundEntity =
    RoundEntity(
        dateTime = date,
        gameId = gameId
    )
