package com.szoldapps.weliwriterkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.szoldapps.weliwriterkmp.data.db.setup.AppDatabase
import com.szoldapps.weliwriterkmp.di.appModules
import com.szoldapps.weliwriterkmp.presentation.navigation.WeliNavigation
import org.koin.compose.KoinApplication

@Composable
internal fun App(appDatabase: AppDatabase) = MaterialTheme {
    KoinApplication(
        application = {
            modules(modules = appModules(appDatabase))
        }
    ) {
        WeliNavigation()
    }
}
