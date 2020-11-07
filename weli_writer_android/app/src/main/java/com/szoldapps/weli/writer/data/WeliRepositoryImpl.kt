package com.szoldapps.weli.writer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.szoldapps.weli.writer.data.dao.GameDao
import com.szoldapps.weli.writer.data.dao.MatchDao
import com.szoldapps.weli.writer.data.mapper.mapToGameEntity
import com.szoldapps.weli.writer.data.mapper.mapToGames
import com.szoldapps.weli.writer.data.mapper.mapToMatch
import com.szoldapps.weli.writer.data.mapper.mapToMatchDb
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.WeliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeliRepositoryImpl @Inject constructor(
    private val matchDao: MatchDao,
    private val gameDao: GameDao,
) : WeliRepository {

    override val matches: LiveData<List<Match>> = Transformations.map(matchDao.getAll()) {
        it.mapToMatch()
    }

    override fun gamesByMatchId(matchId: Int): LiveData<List<Game>> =
        Transformations.map(gameDao.getGamesByMatchById(matchId)) { gameEntities ->
            gameEntities.mapToGames()
        }

    override suspend fun addMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.insertAll(match.mapToMatchDb())
    }

    override suspend fun addGame(game: Game, matchId: Int) = withContext(Dispatchers.IO) {
        gameDao.insertAll(game.mapToGameEntity(matchId))
    }
}
