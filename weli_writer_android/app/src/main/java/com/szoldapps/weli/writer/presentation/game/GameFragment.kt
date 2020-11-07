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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentGameBinding
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.game.GameViewState.*
import com.szoldapps.weli.writer.presentation.game.adapter.GameRvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val binding by viewBinding(FragmentGameBinding::bind)

    private val args: GameFragmentArgs by navArgs()

    private val viewModel: GameViewModel by viewModels()

    private val gameRvAdapter = GameRvAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(gameToolbar)
            gameToolbar.title = "Match: ${args.matchId}"
        }
        binding.gameRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = gameRvAdapter
        }
    }

    private fun handleViewState(viewState: GameViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> gameRvAdapter.refresh(viewState.games)
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
        inflater.inflate(R.menu.menu_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_match_add) {
            viewModel.addRandomGame()
        }
        return super.onOptionsItemSelected(item)
    }

}
