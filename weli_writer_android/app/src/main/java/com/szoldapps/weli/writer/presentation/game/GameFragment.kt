package com.szoldapps.weli.writer.presentation.game

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
import com.szoldapps.weli.writer.databinding.FragmentGameBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.game.GameViewState.Content
import com.szoldapps.weli.writer.presentation.game.GameViewState.Error
import com.szoldapps.weli.writer.presentation.game.GameViewState.Loading
import com.szoldapps.weli.writer.presentation.game.adapter.GameRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Game], including a list of its [Round]s.
 */
@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val binding by viewBinding(FragmentGameBinding::bind)

    private val args: GameFragmentArgs by navArgs()

    private val viewModel: GameViewModel by viewModels()

    private val roundRvAdapter = GameRvAdapter { roundId -> openRoundFragment(roundId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
        viewModel.viewEvent.observe(viewLifecycleOwner, ::handleViewEvent)
        viewModel.loadContent(args.gameId)
    }

    private fun handleViewEvent(viewEvent: GameViewEvent) =
        when (viewEvent) {
            is GameViewEvent.OpenRoundFragment -> openRoundFragment(viewEvent.roundId)
        }

    private fun openRoundFragment(roundId: Long) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToRoundFragment(roundId))
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(gameToolbar)
            gameToolbar.title = "Rounds of Game: ${args.gameId}"
        }
        binding.gameRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = roundRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewState(viewState: GameViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> roundRvAdapter.refresh(viewState.rvItems)
        }
        updateVisibility(viewState)
    }

    private fun updateVisibility(viewState: GameViewState) {
        with(binding) {
            gameLoadingSpinner.isVisible = viewState is Loading
            gameErrorTv.isVisible = viewState is Error
            gameRv.isVisible = viewState is Content
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_game, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_game_add_round) {
            viewModel.addRandomRound()
        }
        return super.onOptionsItemSelected(item)
    }

}
