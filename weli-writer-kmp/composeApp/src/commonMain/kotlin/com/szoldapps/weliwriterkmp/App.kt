package com.szoldapps.weliwriterkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.szoldapps.weliwriterkmp.presentation.common.click_action.LongUnitClickAction
import com.szoldapps.weliwriterkmp.appDatabase.AppDatabase
import com.szoldapps.weliwriterkmp.di.appModules
import com.szoldapps.weliwriterkmp.presentation.match_list.MatchListScreen
import org.koin.compose.KoinApplication

@Composable
internal fun App(appDatabase: AppDatabase) = MaterialTheme {
    KoinApplication(
        application = {
            modules(modules = appModules(appDatabase))
        }
    ) {
        MatchListScreen(
            onMatchListItemClickAction = LongUnitClickAction { /* TBD */ }
        )
    }
}
