package com.szoldapps.weliwriterkmp.presentation.match_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.szoldapps.weli.writer.presentation.common.click_action.ClickAction
import com.szoldapps.weli.writer.presentation.common.click_action.LongUnitClickAction
import com.szoldapps.weliwriterkmp.domain.Match
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import weliwriterkmp.composeapp.generated.resources.Res
import weliwriterkmp.composeapp.generated.resources.match_menu_title
import weliwriterkmp.composeapp.generated.resources.match_toolbar_title

/**
 * Stateful
 */
@Composable
fun MatchListScreen(
    viewModel: MatchListViewModel = koinInject(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onAddItemClickListener: () -> (Unit) = { },
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.match_toolbar_title)
            )
        },
        actions = {
            IconButton(
                onClick = { onAddItemClickListener.invoke() },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(Res.string.match_menu_title)
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

@Composable
private fun MatchRow(
    match: Match,
    onItemClickListener: (Long) -> (Unit),
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        onClick = {
            onItemClickListener.invoke(match.id)
        }
    ) {
        Text(
            text = "${match.date.mapToFormattedDateString()}, ${match.location}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

private fun LocalDateTime.mapToFormattedDateString(): String =
    this.format(LocalDateTime.Formats.ISO)

@Preview
@Composable
private fun Loading() {
    Text(
        text = "LOADING ...",
        style = MaterialTheme.typography.headlineMedium,
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
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
    )
}
