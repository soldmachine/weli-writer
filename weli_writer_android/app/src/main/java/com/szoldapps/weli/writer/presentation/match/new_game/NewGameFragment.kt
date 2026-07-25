package com.szoldapps.weli.writer.presentation.match.new_game

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentNewGameBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.Content
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.Error
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewState.Loading
import com.szoldapps.weli.writer.presentation.match.new_game.adapter.PlayerRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Game], including a list of its [Round]s.
 */
@AndroidEntryPoint
class NewGameFragment : Fragment(R.layout.fragment_new_game) {

    private val binding by viewBinding(FragmentNewGameBinding::bind)

    private val args: NewGameFragmentArgs by navArgs()

    private val sharedViewModel: NewGameViewModel by activityViewModels()

    private val playerRvAdapter = PlayerRvAdapter { index ->
        findNavController().navigate(
            NewGameFragmentDirections.actionNewGameFragmentToSelectPlayerFragment(index)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupEdgeToEdge()

        setupToolbarAndRv()
        sharedViewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
        sharedViewModel.viewEvent.observe(viewLifecycleOwner, ::handleViewEvent)
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.newGameToolbar.updatePadding(top = insets.top)
            binding.newGameButton.updateLayoutParams<MarginLayoutParams> {
                val density = v.resources.displayMetrics.density
                bottomMargin = insets.bottom + (16 * density).toInt()
            }
            windowInsets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_game, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_new_game_reset) {
            sharedViewModel.resetSelectedPlayers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleViewEvent(viewEvent: NewGameViewEvent) {
        when (viewEvent) {
            is NewGameViewEvent.OpenGameFragment -> {
                findNavController().navigate(
                    NewGameFragmentDirections.actionNewGameFragmentToGameFragment(viewEvent.gameId)
                )
            }
        }
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
        binding.newGameButton.setOnClickListener { sharedViewModel.createGame(args.matchId) }
    }

    private fun handleViewState(viewState: NewGameViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> handleContent(viewState)
        }
        updateVisibility(viewState)
    }

    private fun handleContent(content: Content) {
        playerRvAdapter.refresh(content.players)
        binding.newGameButton.isEnabled = content.players.none { it == null }
    }

    private fun updateVisibility(viewState: NewGameViewState) {
        with(binding) {
            newGameLoadingSpinner.isVisible = viewState is Loading
            newGameErrorTv.isVisible = viewState is Error
            newGameRv.isVisible = viewState is Content
        }
    }

}
