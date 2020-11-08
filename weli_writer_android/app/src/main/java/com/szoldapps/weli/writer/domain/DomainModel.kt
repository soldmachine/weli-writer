package com.szoldapps.weli.writer.domain

import org.threeten.bp.OffsetDateTime

data class Match(
    val id: Long = -1,
    val date: OffsetDateTime,
    val location: String
)

data class Player(
    val id: Long = -1,
    val firstName: String,
    val lastName: String
)

data class Game(
    val id: Long = -1,
    val date: OffsetDateTime,
    val players: List<Player> = emptyList()
)

data class Round(
    val id: Long = -1,
    val date: OffsetDateTime,
)

data class RoundValue(
    val id: Long = -1,
    val date: OffsetDateTime,
    val number: Int,
    val value: Int
)
