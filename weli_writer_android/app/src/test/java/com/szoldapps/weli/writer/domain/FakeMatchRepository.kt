package com.szoldapps.weli.writer.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeMatchRepository : MatchRepository {

    private val _matches = MutableSharedFlow<List<Match>>()

    var addMatchWasCalled = false

    suspend fun emit(value: List<Match>) = _matches.emit(value)

    override val matches: Flow<List<Match>> = _matches

    override suspend fun addMatch(match: Match) {
        addMatchWasCalled = true
    }
}
