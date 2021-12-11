package com.szoldapps.weli.writer.presentation.round.overview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentRoundBinding
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.RoundValue
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewEvent.OpenAddRoundValueFragment
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewState.Content
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewState.Error
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewState.Loading
import com.szoldapps.weli.writer.presentation.round.overview.adapter.RoundValueRvAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a [Round], including a list of its [RoundValue]s.
 */
@AndroidEntryPoint
class RoundFragment : Fragment(R.layout.fragment_round) {

    private val binding by viewBinding(FragmentRoundBinding::bind)

    private val args: RoundFragmentArgs by navArgs()

    private val viewModel: RoundViewModel by viewModels()

    private val roundRvAdapter = RoundValueRvAdapter { roundValueNumber -> openAddRoundValueFragment(roundValueNumber) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
        viewModel.viewEvent.observe(viewLifecycleOwner, ::handleViewEvent)
        viewModel.loadContent(args.roundId)
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
            is Content -> roundRvAdapter.refresh(viewState.indexOfRound, viewState.rounds)
        }
        updateVisibility(viewState)
    }

    private fun handleViewEvent(viewEvent: RoundViewEvent) {
        when (viewEvent) {
            OpenAddRoundValueFragment -> openAddRoundValueFragment()
        }
    }

    private fun updateVisibility(viewState: RoundViewState) {
        with(binding) {
            roundLoadingSpinner.isVisible = viewState is Loading
            roundErrorTv.isVisible = viewState is Error
            roundRv.isVisible = viewState is Content
        }
    }

    private fun openAddRoundValueFragment(roundValueNumber: Int = -1) {
        findNavController().navigate(
            RoundFragmentDirections.actionRoundFragmentToAddRoundValueBottomSheet(args.roundId, roundValueNumber)
        )
    }
}
