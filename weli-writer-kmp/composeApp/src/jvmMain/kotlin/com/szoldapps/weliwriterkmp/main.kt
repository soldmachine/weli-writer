package com.szoldapps.weliwriterkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.szoldapps.weliwriterkmp.data.db.setup.DBFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "WeliWriterKmp",
    ) {
        val appDatabase = DBFactory().createDatabase()
        App(appDatabase = appDatabase)
    }
}
