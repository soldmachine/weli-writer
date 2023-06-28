package com.szoldapps.weli.writer.presentation.match_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.presentation.common.click_action.ClickAction
import com.szoldapps.weli.writer.presentation.common.click_action.LongUnitClickAction
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Stateful
 */
@Composable
fun MatchListScreen(
    viewModel: MatchListViewModel = viewModel(),
    onMatchListItemClickAction: LongUnitClickAction,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MatchListScreen(
        uiState = uiState,
        onAddMatchClickAction = ClickAction { viewModel.addRandomMatch() },
        onMatchListItemClickAction = onMatchListItemClickAction,
    )
}

/**
 * Stateless
 */
@Composable
private fun MatchListScreen(
    uiState: MatchListUiState,
    onAddMatchClickAction: ClickAction,
    onMatchListItemClickAction: LongUnitClickAction,
) {
    Column {
        TopBar(
            onAddItemClickListener = { onAddMatchClickAction.invoke(Unit) }
        )
        when (uiState) {
            is MatchListUiState.Content -> Content(uiState, onMatchListItemClickAction)
            MatchListUiState.Error -> Error()
            MatchListUiState.Loading -> Loading()
        }
    }
}

@Composable
private fun TopBar(
    onAddItemClickListener: () -> (Unit) = { },
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.match_toolbar_title)) },
        actions = {
            IconButton(
                onClick = { onAddItemClickListener.invoke() },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.match_menu_title)
                    )
                }
            )
        }
    )
}

@Composable
private fun Content(
    contentUiState: MatchListUiState.Content,
    onItemClickListener: (Long) -> (Unit) = { },
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
    ) {
        contentUiState.matches.forEach { message ->
            MatchRow(message, onItemClickListener)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MatchRow(
    match: Match,
    onItemClickListener: (Long) -> (Unit),
) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        onClick = {
            onItemClickListener.invoke(match.id)
        }
    ) {
        Text(
            text = "${match.date.mapToFormattedDateString()}, ${match.location}",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

private fun OffsetDateTime.mapToFormattedDateString(): String =
    this.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))

@Preview
@Composable
private fun Loading() {
    Text(
        text = "LOADING ...",
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
    )
}

@Composable
private fun Error() {
    Text(
        text = "ERROR ...",
        style = MaterialTheme.typography.h1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
    )
}
