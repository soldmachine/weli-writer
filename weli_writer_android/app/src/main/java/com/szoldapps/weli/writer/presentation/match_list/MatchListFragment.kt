package com.szoldapps.weli.writer.presentation.match_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Content
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Error
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Loading
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Shows a list of [Match]es
 */
@AndroidEntryPoint
class MatchListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    MatchListScaffold(
                        onItemClickListener = { matchId ->
                            findNavController().navigate(
                                MatchListFragmentDirections.actionMatchListFragmentToMatchFragment(matchId)
                            )
                        }
                    )
                }
            }
        }
}

@Composable
fun MatchListScaffold(
    viewModel: MatchListViewModel = viewModel(),
    onItemClickListener: (Long) -> (Unit) = { },
) {
    Scaffold(
        topBar = {
            TopBar(
                onAddItemClickListener = { viewModel.addRandomMatch() }
            )
        },
        content = {
            MatchList(
                onItemClickListener = { matchId ->
                    onItemClickListener.invoke(matchId)
                }
            )
        }
    )
}

@Composable
fun TopBar(
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
fun MatchList(
    viewModel: MatchListViewModel = viewModel(),
    onItemClickListener: (Long) -> (Unit) = { },
) {
    val viewState by viewModel.viewState.observeAsState()
    @Suppress("UnnecessaryVariable")
    when (val state = viewState) {
        is Content ->
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                state.matches.forEach { message ->
                    MatchRow(message, onItemClickListener)
                }
            }
        Error -> Error()
        Loading,
        null -> Loading()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MatchRow(
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

fun OffsetDateTime.mapToFormattedDateString(): String = this.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))

@Preview
@Composable
fun Loading() {
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
fun Error() {
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
