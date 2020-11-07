package com.szoldapps.weli.writer.presentation.match_list

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.databinding.FragmentMatchListBinding
import com.szoldapps.weli.writer.presentation.common.helper.viewBinding
import com.szoldapps.weli.writer.presentation.match_list.MatchViewState.*
import com.szoldapps.weli.writer.presentation.match_list.adapter.MatchListRvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchListFragment : Fragment(R.layout.fragment_match_list) {

    private val binding by viewBinding(FragmentMatchListBinding::bind)

    private val listViewModel: MatchListViewModel by viewModels()

    private val matchRvAdapter = MatchListRvAdapter { matchId ->
        findNavController().navigate(MatchListFragmentDirections.actionMatchFragmentToGameFragment(matchId))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbarAndRv()
        listViewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private fun setupToolbarAndRv() {
        (activity as AppCompatActivity).setSupportActionBar(binding.matchToolbar)
        binding.matchRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = matchRvAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewState(viewState: MatchViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> matchRvAdapter.refresh(viewState.matches)
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
            listViewModel.addRandomMatch()
        }
        return super.onOptionsItemSelected(item)
    }

}
