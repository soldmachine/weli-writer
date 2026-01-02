package com.szoldapps.weliwriterkmp.data

import com.szoldapps.weliwriterkmp.data.db.dao.MatchDao
import com.szoldapps.weliwriterkmp.data.db.mapper.mapToMatch
import com.szoldapps.weliwriterkmp.data.db.mapper.mapToMatchDb
import com.szoldapps.weliwriterkmp.domain.Match
import com.szoldapps.weliwriterkmp.domain.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MatchRepositoryImpl(
    private val matchDao: MatchDao,
) : MatchRepository {

    override val matches: Flow<List<Match>> = matchDao.getAll().map { it.mapToMatch() }

    override suspend fun addMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.insertAll(match.mapToMatchDb())
    }
}
