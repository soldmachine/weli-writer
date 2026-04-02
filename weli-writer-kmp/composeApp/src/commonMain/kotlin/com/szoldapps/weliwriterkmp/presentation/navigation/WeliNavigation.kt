package com.szoldapps.weliwriterkmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.szoldapps.weliwriterkmp.presentation.common.click_action.LongUnitClickAction
import com.szoldapps.weliwriterkmp.presentation.match_detail.MatchDetailScreen
import com.szoldapps.weliwriterkmp.presentation.match_list.MatchListScreen

@Composable
fun WeliNavigation() {
    val backStack = rememberNavBackStack(navConfig, MatchListRoute)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<MatchListRoute> {
                MatchListScreen(
                    onMatchListItemClickAction = LongUnitClickAction { matchId ->
                        backStack.add(MatchDetailRoute(matchId))
                    }
                )
            }
            entry<MatchDetailRoute> { route ->
                MatchDetailScreen(matchId = route.matchId)
            }
        }
    )
}
