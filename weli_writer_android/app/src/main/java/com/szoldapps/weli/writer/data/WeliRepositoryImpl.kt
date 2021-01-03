package com.szoldapps.weli.writer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.szoldapps.weli.writer.data.db.dao.*
import com.szoldapps.weli.writer.data.db.mapper.*
import com.szoldapps.weli.writer.domain.*
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowValues
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeliRepositoryImpl @Inject constructor(
    private val matchDao: MatchDao,
    private val playerDao: PlayerDao,
    private val playerGameDao: PlayerGameDao,
    private val gameDao: GameDao,
    private val roundDao: RoundDao,
    private val roundValueDao: RoundValueDao,
) : WeliRepository {

    override val matches: LiveData<List<Match>> = Transformations.map(matchDao.getAll()) { it.mapToMatch() }

    override val players: LiveData<List<Player>> = Transformations.map(playerDao.getAll()) { it.mapToPlayers() }

    override fun gamesByMatchId(matchId: Long): LiveData<List<Game>> =
        Transformations.map(playerGameDao.getGamesWithPlayersEntities(matchId)) { gamesWithPlayers ->
            gamesWithPlayers.mapToGames()
        }

    override fun roundsByGameId(gameId: Long): LiveData<List<Round>> =
        Transformations.map(roundDao.getRoundsByGameById(gameId)) { roundEntities ->
            roundEntities.mapToRounds()
        }

    override fun roundRowValuesByRoundId(roundId: Long): LiveData<List<RoundRowValues>> =
        Transformations.map(roundValueDao.getRoundValueByRoundId(roundId)) { roundValueEntities ->
            roundValueEntities.mapToRoundRowValues()
        }

    override suspend fun roundValueCountByRoundId(roundId: Long): Int = withContext(Dispatchers.IO) {
        roundValueDao.getRoundValueCountByRoundId(roundId) / 4
    }

    override suspend fun addMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.insertAll(match.mapToMatchDb())
    }

    override suspend fun addGame(game: Game, matchId: Long) = withContext(Dispatchers.IO) {
        playerGameDao.insert(game, matchId)
    }

    override suspend fun addRound(round: Round, gameId: Long): Long = withContext(Dispatchers.IO) {
        roundDao.insert(round.mapToRoundEntity(gameId))
    }

    override suspend fun addRoundValue(
        roundValue: RoundValue,
        roundId: Long,
        player: Player
    ) = withContext(Dispatchers.IO) {
        roundValueDao.insertAll(roundValue.mapToRoundValueEntity(roundId, player.id))
    }

    override suspend fun addPlayer(player: Player) = withContext(Dispatchers.IO) {
        playerDao.insertAll(player.mapToPlayerEntity())
    }

    override suspend fun getPlayersOfRound(roundId: Long): List<Player> = withContext(Dispatchers.IO) {
        roundDao.getPlayersOfRound(roundId).mapToPlayers()
    }
}
