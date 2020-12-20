package com.szoldapps.weli.writer.presentation.match.select_player

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
import com.szoldapps.weli.writer.databinding.FragmentSelectPlayerBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match.new_game.SharedNewGameViewModel
import com.szoldapps.weli.writer.presentation.match.select_player.SelectPlayerViewState.*
import com.szoldapps.weli.writer.presentation.match.select_player.adapter.PlayerRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Game], including a list of its [Round]s.
 */
@AndroidEntryPoint
class SelectPlayerFragment : Fragment(R.layout.fragment_select_player) {

    private val navArgs: SelectPlayerFragmentArgs by navArgs()

    private val binding by viewBinding(FragmentSelectPlayerBinding::bind)

    private val viewModel: SelectPlayerViewModel by viewModels()

    private val sharedViewModel: SharedNewGameViewModel by activityViewModels()

    private val playerRvAdapter = PlayerRvAdapter { player ->
        sharedViewModel.selectPlayer(navArgs.playerIndex, player)
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(selectPlayerToolbar)
            selectPlayerToolbar.title =
                getString(R.string.select_player_toolbar_title) + " (index=${navArgs.playerIndex})"
        }
        binding.selectPlayerRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = playerRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewState(viewState: SelectPlayerViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> handleContent(viewState)
        }
        updateVisibility(viewState)
    }

    private fun handleContent(content: Content) {
        playerRvAdapter.refresh(content.players)
    }

    private fun updateVisibility(viewState: SelectPlayerViewState) {
        with(binding) {
            selectPlayerLoadingSpinner.isVisible = viewState is Loading
            selectPlayerErrorTv.isVisible = viewState is Error
            selectPlayerRv.isVisible = viewState is Content
        }
    }

}
