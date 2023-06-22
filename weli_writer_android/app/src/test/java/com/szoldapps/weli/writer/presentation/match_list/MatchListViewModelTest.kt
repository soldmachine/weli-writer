package com.szoldapps.weli.writer.presentation.match_list

import com.szoldapps.weli.writer.common.CoroutineTestExtension
import com.szoldapps.weli.writer.domain.FakeMatchRepository
import com.szoldapps.weli.writer.domain.Match
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.threeten.bp.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutineTestExtension::class)
internal class MatchListViewModelTest {

    @Test
    fun `test uiState with one match`() = runTest {
        // given
        val match = Match(id = -1, date = OffsetDateTime.now(), location = "some location")
        val matchList = listOf(match)
        val fakeMatchRepository = FakeMatchRepository()

        // when
        val viewModel = MatchListViewModel(fakeMatchRepository)

        // At least one collector needed for StateFlow,
        // because of .stateIn(SharingStarted.WhileSubscribed())
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        // then
        assertEquals(MatchViewState.Loading, viewModel.uiState.value)

        fakeMatchRepository.emit(matchList)
        assertEquals(MatchViewState.Content(matchList), viewModel.uiState.value)
    }
}
