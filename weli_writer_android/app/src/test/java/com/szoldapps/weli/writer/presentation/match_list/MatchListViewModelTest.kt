package com.szoldapps.weli.writer.presentation.match_list

import com.szoldapps.weli.writer.common.CoroutineTestExtension
import com.szoldapps.weli.writer.domain.FakeMatchRepository
import com.szoldapps.weli.writer.domain.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.threeten.bp.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutineTestExtension::class)
internal class MatchListViewModelTest {

    @Test
    fun `initial uiState is Loading`() = runTest {
        // given
        val fakeMatchRepository = FakeMatchRepository()

        // when
        val viewModel = MatchListViewModel(fakeMatchRepository)

        // then
        assertEquals(MatchListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `correct uiState with empty match list`() = runTest {
        // given
        val matchList = emptyList<Match>()
        val fakeMatchRepository = FakeMatchRepository()

        // when
        val viewModel = MatchListViewModel(fakeMatchRepository)
        backgroundScope.startUiStateCollectorOf(viewModel)

        // then
        assertEquals(MatchListUiState.Loading, viewModel.uiState.value)
        fakeMatchRepository.emit(matchList)
        assertEquals(MatchListUiState.Content(matchList), viewModel.uiState.value)
    }

    @Test
    fun `correct uiState with one match`() = runTest {
        // given
        val match = Match(id = -1, date = OffsetDateTime.now(), location = "some location")
        val matchList = listOf(match)
        val fakeMatchRepository = FakeMatchRepository()

        // when
        val viewModel = MatchListViewModel(fakeMatchRepository)
        backgroundScope.startUiStateCollectorOf(viewModel)

        // then
        assertEquals(MatchListUiState.Loading, viewModel.uiState.value)
        fakeMatchRepository.emit(matchList)
        assertEquals(MatchListUiState.Content(matchList), viewModel.uiState.value)
    }

    @Test
    fun `correct uiState with multiple matches`() = runTest {
        // given
        val match = Match(id = 1, date = OffsetDateTime.now(), location = "some location")
        val matchList = listOf(match, match.copy(id = 2), match.copy(id = 3), match.copy(id = 4))
        val fakeMatchRepository = FakeMatchRepository()

        // when
        val viewModel = MatchListViewModel(fakeMatchRepository)
        backgroundScope.startUiStateCollectorOf(viewModel)
        fakeMatchRepository.emit(matchList)

        // then
        assertEquals(MatchListUiState.Content(matchList), viewModel.uiState.value)
    }


    @Test
    fun `addMatch calls repository correctly`() = runTest {
        // given
        val fakeMatchRepository = FakeMatchRepository()
        val viewModel = MatchListViewModel(fakeMatchRepository)

        // when
        viewModel.addRandomMatch()

        // then
        assertTrue(fakeMatchRepository.addMatchWasCalled)
    }

    /**
     * At least one collector needed for StateFlow, because of .stateIn(SharingStarted.WhileSubscribed())
     */
    private fun CoroutineScope.startUiStateCollectorOf(viewModel: MatchListViewModel) {
        this.launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }
    }
}
