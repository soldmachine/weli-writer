package com.szoldapps.weliwriterkmp.data.db.setup

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

actual class DBFactory {

    actual fun createDatabase(): AppDatabase {
        val absolutePathToDbFile = File(System.getProperty("java.io.tmpdir"), dbFileName).absolutePath
        println("DB stored in = $absolutePathToDbFile")
        return Room.databaseBuilder<AppDatabase>(absolutePathToDbFile)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
