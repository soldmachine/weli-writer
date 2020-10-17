package com.szoldapps.weli.writer.data

import com.szoldapps.weli.writer.data.dao.MatchDao
import com.szoldapps.weli.writer.data.mapper.mapToMatch
import com.szoldapps.weli.writer.data.mapper.mapToMatchDb
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.WeliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeliRepositoryImpl @Inject constructor(
    private val matchDao: MatchDao
) : WeliRepository {

    override suspend fun getMatches(): List<Match> = withContext(Dispatchers.IO) {
        return@withContext matchDao.getAll().mapToMatch()
    }

    override suspend fun addMatch(match: Match) {
        withContext(Dispatchers.IO) {
            matchDao.insertAll(match.mapToMatchDb())
        }
    }
}
