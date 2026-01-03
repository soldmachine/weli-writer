package com.szoldapps.weliwriterkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.szoldapps.weliwriterkmp.data.db.setup.DBFactory

fun MainViewController() =
    ComposeUIViewController {
        val appDatabase = DBFactory().createDatabase()
        App(appDatabase = appDatabase)
    }
