package com.szoldapps.weli.writer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.szoldapps.weli.writer.data.dao.GameDao
import com.szoldapps.weli.writer.data.dao.MatchDao
import com.szoldapps.weli.writer.data.dao.RoundDao
import com.szoldapps.weli.writer.data.mapper.*
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.WeliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeliRepositoryImpl @Inject constructor(
    private val matchDao: MatchDao,
    private val gameDao: GameDao,
    private val roundDao: RoundDao,
) : WeliRepository {

    override val matches: LiveData<List<Match>> = Transformations.map(matchDao.getAll()) {
        it.mapToMatch()
    }

    override fun gamesByMatchId(matchId: Int): LiveData<List<Game>> =
        Transformations.map(gameDao.getGamesByMatchById(matchId)) { gameEntities ->
            gameEntities.mapToGames()
        }

    override fun roundsByGameId(gameId: Int): LiveData<List<Round>> =
        Transformations.map(roundDao.getRoundsByGameById(gameId)) { roundEntities ->
            roundEntities.mapToRounds()
        }

    override suspend fun addMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.insertAll(match.mapToMatchDb())
    }

    override suspend fun addGame(game: Game, matchId: Int) = withContext(Dispatchers.IO) {
        gameDao.insertAll(game.mapToGameEntity(matchId))
    }

    override suspend fun addRound(round: Round, gameId: Int) = withContext(Dispatchers.IO) {
        roundDao.insertAll(round.mapToRoundEntity(gameId))
    }
}
