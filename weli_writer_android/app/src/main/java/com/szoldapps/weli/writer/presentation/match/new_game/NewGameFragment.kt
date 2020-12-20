package com.szoldapps.weli.writer.presentation.match.new_game

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentNewGameBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Player
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.*
import com.szoldapps.weli.writer.presentation.match.new_game.adapter.PlayerRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Game], including a list of its [Round]s.
 */
@AndroidEntryPoint
class NewGameFragment : Fragment(R.layout.fragment_new_game) {

    private val binding by viewBinding(FragmentNewGameBinding::bind)

    private val args: NewGameFragmentArgs by navArgs()

    private val viewModel: NewGameViewModel by viewModels()

    private val sharedViewModel: SharedNewGameViewModel by activityViewModels()

    private val playerRvAdapter = PlayerRvAdapter { index ->
        findNavController().navigate(
            NewGameFragmentDirections.actionNewGameFragmentToSelectPlayerFragment(index)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
        sharedViewModel.selectedPlayers.observe(viewLifecycleOwner, ::handleSelectedPlayers)
    }

    private fun handleSelectedPlayers(list: List<Player?>) {
        playerRvAdapter.refresh(list)
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(newGameToolbar)
            newGameToolbar.title = getString(R.string.new_game_toolbar_title, args.matchId)
        }
        binding.newGameRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = playerRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewState(viewState: NewGameViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> Unit
        }
        updateVisibility(viewState)
    }

    private fun updateVisibility(viewState: NewGameViewState) {
        with(binding) {
            newGameLoadingSpinner.isVisible = viewState is Loading
            newGameErrorTv.isVisible = viewState is Error
            newGameRv.isVisible = viewState is Content
        }
        binding.newGameRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = playerRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

}
