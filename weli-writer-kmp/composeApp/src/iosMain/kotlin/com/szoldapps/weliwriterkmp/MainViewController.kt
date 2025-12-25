package com.szoldapps.weliwriterkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.szoldapps.weliwriterkmp.appDatabase.DBFactory

fun MainViewController() =
    ComposeUIViewController {
        val appDatabase = DBFactory().createDatabase()
        App(appDatabase = appDatabase)
    }
