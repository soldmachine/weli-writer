package com.szoldapps.weli.writer.presentation.match_list

import com.szoldapps.weli.writer.domain.FakeWeliRepository
import com.szoldapps.weli.writer.domain.Match
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.threeten.bp.OffsetDateTime

internal class MatchListViewModelTest {

//    @Test
//    fun getUiState() = runTest {
//        // given
//        val match = Match(id = -1, date = OffsetDateTime.now(), location = "some location")
//        val matchList = listOf(match)
//        val weliRepository = FakeWeliRepository(matchList)
//
//        // when
//        val viewModel = MatchListViewModel(weliRepository)
//
//        // then
//        println(viewModel.uiState.toList().toString())
//        //assertEquals(MatchViewState.Content(matchList), uiState.drop(1).first())
//    }
}
