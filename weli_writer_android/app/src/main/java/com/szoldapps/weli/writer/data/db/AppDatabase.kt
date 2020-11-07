package com.szoldapps.weli.writer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.szoldapps.weli.writer.data.dao.GameDao
import com.szoldapps.weli.writer.data.dao.MatchDao
import com.szoldapps.weli.writer.data.dao.PlayerDao
import com.szoldapps.weli.writer.data.db.converter.DateTypeConverter
import com.szoldapps.weli.writer.data.entity.GameEntity
import com.szoldapps.weli.writer.data.entity.MatchEntity
import com.szoldapps.weli.writer.data.entity.PlayerEntity

@Database(
    entities = [
        PlayerEntity::class,
        MatchEntity::class,
        GameEntity::class,
    ],
    version = 3
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun matchDao(): MatchDao

    abstract fun gameDao(): GameDao

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
