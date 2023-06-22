package com.szoldapps.weli.writer.data

import com.szoldapps.weli.writer.data.db.dao.MatchDao
import com.szoldapps.weli.writer.data.db.mapper.mapToMatch
import com.szoldapps.weli.writer.data.db.mapper.mapToMatchDb
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchDao: MatchDao,
) : MatchRepository {

    override val matches: Flow<List<Match>> = matchDao.getAll().map { it.mapToMatch() }

    override suspend fun addMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.insertAll(match.mapToMatchDb())
    }
}