package com.szoldapps.weli.writer.domain

import androidx.lifecycle.LiveData

interface WeliRepository {

    val matches: LiveData<List<Match>>

    val players: LiveData<List<Player>>

    fun gamesByMatchId(matchId: Long): LiveData<List<Game>>

    suspend fun gameRvAdapterItemsByGameId(gameId: Long): List<GameRvAdapterItem>

    suspend fun roundValueRvAdapterItemsByRoundId(
        roundId: Long,
        onButtonClickListener: () -> (Unit),
    ): List<RoundValueRvAdapterItem>

    suspend fun roundValueCountByRoundId(roundId: Long): Int

    suspend fun addMatch(match: Match)

    suspend fun addGame(game: Game, matchId: Long): Long

    suspend fun addRound(round: Round, gameId: Long): Long

    suspend fun addRoundValue(roundValue: RoundValue, roundId: Long, player: Player)

    suspend fun getPlayersOfRound(roundId: Long): List<Player>

    suspend fun addPlayer(player: Player)

    suspend fun getPlayerInitialsOfRound(roundId: Long): List<String>

    suspend fun getTricksByRoundIdAndNumber(roundId: Long, roundNumber: Int): List<Int>

    suspend fun updateRoundValues(roundId: Long, roundNumber: Int, tricks: List<Int>)

    suspend fun getIndexOfRoundInGameByRoundId(roundId: Long): Int
}
