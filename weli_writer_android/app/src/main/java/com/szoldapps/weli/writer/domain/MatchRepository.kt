package com.szoldapps.weli.writer.domain

import kotlinx.coroutines.flow.Flow

interface MatchRepository {

    val matches: Flow<List<Match>>

    suspend fun addMatch(match: Match)
}