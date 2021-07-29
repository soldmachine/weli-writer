package com.szoldapps.weli.writer.presentation.match_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.composethemeadapter.MdcTheme
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentMatchListBinding
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Content
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Error
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.Loading
import com.szoldapps.weli.writer.presentation.match_list.adapter.MatchListRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a list of [Match]es
 */
@AndroidEntryPoint
class MatchListFragment : Fragment(R.layout.fragment_match_list) {

    private val binding by viewBinding(FragmentMatchListBinding::bind)

    private val listViewModel: MatchListViewModel by viewModels()

    private val matchRvAdapter = MatchListRvAdapter { matchId ->
        findNavController().navigate(MatchListFragmentDirections.actionMatchListFragmentToMatchFragment(matchId))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbarAndRv()
        listViewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)

        val greeting = view.findViewById<ComposeView>(R.id.greeting)
        greeting.setContent {
            MdcTheme {
                Greeting()
            }
        }
    }

    private fun setupToolbarAndRv() {
        (activity as AppCompatActivity).setSupportActionBar(binding.matchToolbar)
        binding.matchRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = matchRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewState(viewState: MatchViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> matchRvAdapter.refresh(viewState.matches)
        }
        updateVisibility(viewState)
    }

    private fun updateVisibility(viewState: MatchViewState) {
        with(binding) {
            matchLoadingSpinner.isVisible = viewState is Loading
            matchErrorTv.isVisible = viewState is Error
            matchRv.isVisible = viewState is Content
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_match_add) {
            listViewModel.addRandomMatch()
        }
        return super.onOptionsItemSelected(item)
    }

}

@Composable
private fun Greeting() {
    Text(
        text = "Woooohoooo Compose is stable and works!",
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
