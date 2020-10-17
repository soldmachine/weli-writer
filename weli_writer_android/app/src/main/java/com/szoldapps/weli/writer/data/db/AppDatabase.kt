package com.szoldapps.weli.writer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.szoldapps.weli.writer.data.dao.MatchDao
import com.szoldapps.weli.writer.data.dao.PlayerDao
import com.szoldapps.weli.writer.data.db.converter.DateTypeConverter
import com.szoldapps.weli.writer.data.entity.MatchDb
import com.szoldapps.weli.writer.data.entity.Player

@Database(
    entities = [
        Player::class,
        MatchDb::class
    ],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun matchDao(): MatchDao

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