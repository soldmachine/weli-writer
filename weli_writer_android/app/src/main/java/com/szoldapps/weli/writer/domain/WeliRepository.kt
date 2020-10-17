package com.szoldapps.weli.writer.domain

interface WeliRepository {

    suspend fun getMatches(): List<Match>

    suspend fun addMatch(match: Match)
}
