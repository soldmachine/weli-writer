package com.szoldapps.weliwriterkmp.data.db.setup

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.szoldapps.weliwriterkmp.data.db.setup.AppDatabase
import com.szoldapps.weliwriterkmp.data.db.setup.dbFileName
import kotlinx.coroutines.Dispatchers

actual class DBFactory(private val context: Context) {

    actual fun createDatabase(): AppDatabase {
        val dbFile = context.getDatabasePath(dbFileName)
        return Room.databaseBuilder<AppDatabase>(context, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
