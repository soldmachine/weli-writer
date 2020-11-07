package com.szoldapps.weli.writer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.szoldapps.weli.writer.data.dao.*
import com.szoldapps.weli.writer.data.db.converter.DateTypeConverter
import com.szoldapps.weli.writer.data.db.dao.*
import com.szoldapps.weli.writer.data.db.entity.*
import com.szoldapps.weli.writer.data.entity.*

@Database(
    entities = [
        PlayerEntity::class,
        MatchEntity::class,
        GameEntity::class,
        RoundEntity::class,
        RoundValueEntity::class,
    ],
    version = 4
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun matchDao(): MatchDao

    abstract fun gameDao(): GameDao

    abstract fun roundDao(): RoundDao

    abstract fun roundValueDao(): RoundValueDao

    companion object {

        private const val APP_DATABASE_NAME = "weli-writer-db"

        fun createDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                APP_DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
