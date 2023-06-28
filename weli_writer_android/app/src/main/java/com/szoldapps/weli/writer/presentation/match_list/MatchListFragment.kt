package com.szoldapps.weli.writer.presentation.match_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.presentation.common.click_action.LongUnitClickAction
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows a list of [Match]es
 */
@AndroidEntryPoint
class MatchListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    MatchListScreen(
                        onMatchListItemClickAction = LongUnitClickAction { matchId ->
                            findNavController().navigate(
                                MatchListFragmentDirections.actionMatchListFragmentToMatchFragment(matchId)
                            )
                        }
                    )
                }
            }
        }
}
