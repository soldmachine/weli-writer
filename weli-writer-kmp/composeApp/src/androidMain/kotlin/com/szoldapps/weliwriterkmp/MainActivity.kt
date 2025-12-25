package com.szoldapps.weliwriterkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.szoldapps.weliwriterkmp.appDatabase.DBFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val appDatabase = DBFactory(applicationContext).createDatabase()
            App(appDatabase = appDatabase)
        }
    }
}
