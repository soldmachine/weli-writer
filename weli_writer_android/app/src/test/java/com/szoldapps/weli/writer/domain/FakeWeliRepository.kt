package com.szoldapps.weli.writer.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeliRepository(private val matchList: List<Match>) : WeliRepository {

    override val matches: Flow<List<Match>> = flow {
        emit(matchList)
    }

    override val players: LiveData<List<Player>>
        get() = TODO("Not yet implemented")

    override fun gamesByMatchId(matchId: Long): LiveData<List<Game>> {
        TODO("Not yet implemented")
    }

    override suspend fun gameRvAdapterItemsByGameId(gameId: Long): List<GameRvAdapterItem> {
        TODO("Not yet implemented")
    }

    override suspend fun roundValueRvAdapterItemsByRoundId(
        roundId: Long,
        onButtonClickListener: () -> Unit
    ): List<RoundValueRvAdapterItem> {
        TODO("Not yet implemented")
    }

    override suspend fun roundValueCountByRoundId(roundId: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun addMatch(match: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun addGame(game: Game, matchId: Long): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addRound(round: Round, gameId: Long): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addRoundValue(roundValue: RoundValue, roundId: Long, player: Player) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersOfRound(roundId: Long): List<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun addPlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerInitialsOfRound(roundId: Long): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getTricksByRoundIdAndNumber(roundId: Long, roundNumber: Int): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun updateRoundValues(roundId: Long, roundNumber: Int, tricks: List<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun getIndexOfRoundInGameByRoundId(roundId: Long): Int {
        TODO("Not yet implemented")
    }
}
