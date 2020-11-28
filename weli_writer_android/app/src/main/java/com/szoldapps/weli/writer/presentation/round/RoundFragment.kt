package com.szoldapps.weli.writer.presentation.round

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
import com.szoldapps.weli.writer.databinding.FragmentRoundBinding
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.RoundValue
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.round.RoundViewState.*
import com.szoldapps.weli.writer.presentation.round.adapter.RoundValueRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Round], including a list of its [RoundValue]s.
 */
@AndroidEntryPoint
class RoundFragment : Fragment(R.layout.fragment_round) {

    private val binding by viewBinding(FragmentRoundBinding::bind)

    private val args: RoundFragmentArgs by navArgs()

    private val viewModel: RoundViewModel by viewModels()

    private val roundRvAdapter = RoundValueRvAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private fun setupToolbarAndRv() {
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(roundToolbar)
            roundToolbar.title = "RoundValues of Round: ${args.roundId}"
        }
        binding.roundRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = roundRvAdapter
        }
    }

    private fun handleViewState(viewState: RoundViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> roundRvAdapter.refresh(viewState.rounds)
        }
        updateVisibility(viewState)
    }

    private fun updateVisibility(viewState: RoundViewState) {
        with(binding) {
            roundLoadingSpinner.isVisible = viewState is Loading
            roundErrorTv.isVisible = viewState is Error
            roundRv.isVisible = viewState is Content
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_match_add) {
            viewModel.addRandomRoundValues()
        }
        return super.onOptionsItemSelected(item)
    }

}
