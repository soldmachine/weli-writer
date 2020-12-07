package com.szoldapps.weli.writer.presentation.match.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentMatchBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match.overview.MatchViewState.*
import com.szoldapps.weli.writer.presentation.match.overview.adapter.MatchRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Match], including a list of its [Game]s.
 */
@AndroidEntryPoint
class MatchFragment : Fragment(R.layout.fragment_match) {

    private val binding by viewBinding(FragmentMatchBinding::bind)

    private val args: MatchFragmentArgs by navArgs()

    private val viewModel: MatchViewModel by viewModels()

    private val gameRvAdapter = MatchRvAdapter { gameId ->
        findNavController().navigate(MatchFragmentDirections.actionMatchFragmentToGameFragment(gameId))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(matchToolbar)
            matchToolbar.title = "Games of Match: ${args.matchId}"
        }
        binding.matchRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = gameRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewState(viewState: MatchViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> gameRvAdapter.refresh(viewState.games)
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
            findNavController().navigate(MatchFragmentDirections.actionMatchFragmentToNewGameFragment(args.matchId))
        }
        return super.onOptionsItemSelected(item)
    }

}
