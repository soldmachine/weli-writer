package com.szoldapps.weli.writer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.szoldapps.weli.writer.data.db.converter.DateTypeConverter
import com.szoldapps.weli.writer.data.db.dao.GameDao
import com.szoldapps.weli.writer.data.db.dao.MatchDao
import com.szoldapps.weli.writer.data.db.dao.PlayerDao
import com.szoldapps.weli.writer.data.db.dao.PlayerGameDao
import com.szoldapps.weli.writer.data.db.dao.RoundDao
import com.szoldapps.weli.writer.data.db.dao.RoundValueDao
import com.szoldapps.weli.writer.data.db.entity.GameEntity
import com.szoldapps.weli.writer.data.db.entity.MatchEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerGameEntity
import com.szoldapps.weli.writer.data.db.entity.RoundEntity
import com.szoldapps.weli.writer.data.db.entity.RoundValueEntity

@Database(
    entities = [
        PlayerEntity::class,
        PlayerGameEntity::class,
        MatchEntity::class,
        GameEntity::class,
        RoundEntity::class,
        RoundValueEntity::class,
    ],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun playerGameDao(): PlayerGameDao

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
