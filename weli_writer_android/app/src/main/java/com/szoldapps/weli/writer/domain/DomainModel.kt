package com.szoldapps.weli.writer.domain

import org.threeten.bp.OffsetDateTime

data class Match(
    val id: Int = -1,
    val date: OffsetDateTime,
    val location: String
)

data class Game(
    val id: Int = -1,
    val date: OffsetDateTime,
)

data class Round(
    val id: Long = -1,
    val date: OffsetDateTime,
)
