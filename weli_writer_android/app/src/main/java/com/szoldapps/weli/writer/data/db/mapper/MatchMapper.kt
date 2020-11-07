package com.szoldapps.weli.writer.data.db.mapper

import com.szoldapps.weli.writer.data.db.entity.MatchEntity
import com.szoldapps.weli.writer.domain.Match

fun MatchEntity.mapToMatch(): Match =
    Match(
        id = matchId,
        date = dateTime,
        location = location
    )

fun List<MatchEntity>.mapToMatch(): List<Match> = this.map { matchDb -> matchDb.mapToMatch() }

fun Match.mapToMatchDb(): MatchEntity =
    MatchEntity(
        dateTime = date,
        location = location
    )
