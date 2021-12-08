package com.szoldapps.weli.writer.presentation.match.select_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentSelectPlayerBinding
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match.new_game.NewGameViewModel
import com.szoldapps.weli.writer.presentation.match.select_player.SelectPlayerViewState.Content
import com.szoldapps.weli.writer.presentation.match.select_player.SelectPlayerViewState.Error
import com.szoldapps.weli.writer.presentation.match.select_player.SelectPlayerViewState.Loading
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

    private val sharedViewModel: NewGameViewModel by activityViewModels()

    private val playerRvAdapter = PlayerRvAdapter { player ->
        sharedViewModel.selectPlayer(navArgs.playerIndex, player)
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

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
        playerRvAdapter.refresh(content.players.minus(sharedViewModel.getSelectedPlayers()))
    }

    private fun updateVisibility(viewState: SelectPlayerViewState) {
        with(binding) {
            selectPlayerLoadingSpinner.isVisible = viewState is Loading
            selectPlayerErrorTv.isVisible = viewState is Error
            selectPlayerRv.isVisible = viewState is Content
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_match_add) {
            showDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        val inflatedView = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_text_input, view as ViewGroup, false)
        val inputFirstName = inflatedView.findViewById<TextInputEditText>(R.id.inputFirstName)
        val inputLastName = inflatedView.findViewById<TextInputEditText>(R.id.inputLastName)
        AlertDialog.Builder(requireContext())
            .setTitle("Name")
            .setView(inflatedView)
            .setPositiveButton("ADD") { _, _ ->
                viewModel.addPlayer(
                    inputFirstName.text.toString(),
                    inputLastName.text.toString()
                )
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

}
