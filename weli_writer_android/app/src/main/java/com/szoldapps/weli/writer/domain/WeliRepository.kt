package com.szoldapps.weli.writer.domain

import androidx.lifecycle.LiveData

interface WeliRepository {

    val matches: LiveData<List<Match>>

    fun gamesByMatchId(matchId: Int): LiveData<List<Game>>

    fun roundsByGameId(gameId: Int): LiveData<List<Round>>

    suspend fun addMatch(match: Match)

    suspend fun addGame(game: Game, matchId: Int)

    suspend fun addRound(round: Round, gameId: Int)
}
