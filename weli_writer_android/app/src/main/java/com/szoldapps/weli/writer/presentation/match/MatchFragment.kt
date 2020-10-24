package com.szoldapps.weli.writer.presentation.match

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.presentation.match.MatchViewState.*
import com.szoldapps.weli.writer.presentation.match.adapter.MatchRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_match.*

@AndroidEntryPoint
class MatchFragment : Fragment() {

    private val matchViewModel: MatchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupToolbar()
        initRv()
        matchViewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
    }

    private val matchRvAdapter = MatchRvAdapter()

    private fun initRv() {
        matchRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = matchRvAdapter
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(matchToolbar)
    }

    private fun handleViewState(viewState: MatchViewState) {
        when (viewState) {
            Loading,
            Error -> Unit
            is Content -> matchRvAdapter.updateList(viewState.matches)
        }
        updateVisibility(viewState)
    }

    private fun updateVisibility(viewState: MatchViewState) {
        matchLoadingSpinner.isVisible = viewState is Loading
        matchErrorTv.isVisible = viewState is Error
        matchRv.isVisible = viewState is Content
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_match_add) {
            matchViewModel.addRandomMatch()
        }
        return super.onOptionsItemSelected(item)
    }

}
