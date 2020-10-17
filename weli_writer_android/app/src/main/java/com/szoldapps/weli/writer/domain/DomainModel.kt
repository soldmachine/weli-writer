package com.szoldapps.weli.writer.domain

import org.threeten.bp.OffsetDateTime

data class Match(
    val date: OffsetDateTime,
    val location: String
)