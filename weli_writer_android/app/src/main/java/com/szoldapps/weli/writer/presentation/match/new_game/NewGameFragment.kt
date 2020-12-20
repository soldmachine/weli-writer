package com.szoldapps.weli.writer.presentation.match.new_game

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentNewGameBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Game], including a list of its [Round]s.
 */
@AndroidEntryPoint
class NewGameFragment : Fragment(R.layout.fragment_new_game) {

    private val binding by viewBinding(FragmentNewGameBinding::bind)

    private val args: NewGameFragmentArgs by navArgs()

    private val viewModel: NewGameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(newGameToolbar)
            newGameToolbar.title = getString(R.string.new_game_toolbar_title, args.matchId)
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
            newGameContentGroup.isVisible = viewState is Content
        }
    }

}
