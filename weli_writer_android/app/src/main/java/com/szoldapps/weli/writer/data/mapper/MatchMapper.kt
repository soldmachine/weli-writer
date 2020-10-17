package com.szoldapps.weli.writer.data.mapper

import com.szoldapps.weli.writer.data.entity.MatchDb
import com.szoldapps.weli.writer.domain.Match

fun MatchDb.mapToMatch(): Match =
    Match(
        date = dateTime,
        location = location
    )

fun List<MatchDb>.mapToMatch(): List<Match> = this.map { matchDb -> matchDb.mapToMatch() }

fun Match.mapToMatchDb(): MatchDb =
    MatchDb(
        dateTime = date,
        location = location
    )
