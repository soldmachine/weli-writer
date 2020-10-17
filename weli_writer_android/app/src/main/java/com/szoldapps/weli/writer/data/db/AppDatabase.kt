package com.szoldapps.weli.writer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.szoldapps.weli.writer.data.dao.UserDao
import com.szoldapps.weli.writer.data.entity.User

@Database(
    entities = [
        User::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        private const val APP_DATABASE_NAME = "weli-writer-db"

        fun createDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                APP_DATABASE_NAME
            ).build()
        }
    }
}