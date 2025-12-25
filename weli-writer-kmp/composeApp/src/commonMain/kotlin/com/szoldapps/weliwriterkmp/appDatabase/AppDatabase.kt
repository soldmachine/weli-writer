package com.szoldapps.weliwriterkmp.appDatabase

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.szoldapps.weliwriterkmp.data.db.converter.DateTypeConverter
import com.szoldapps.weliwriterkmp.data.db.dao.GameDao
import com.szoldapps.weliwriterkmp.data.db.dao.MatchDao
import com.szoldapps.weliwriterkmp.data.db.dao.PlayerDao
import com.szoldapps.weliwriterkmp.data.db.dao.PlayerGameDao
import com.szoldapps.weliwriterkmp.data.db.dao.RoundDao
import com.szoldapps.weliwriterkmp.data.db.dao.RoundValueDao
import com.szoldapps.weliwriterkmp.data.db.entity.GameEntity
import com.szoldapps.weliwriterkmp.data.db.entity.MatchEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerGameEntity
import com.szoldapps.weliwriterkmp.data.db.entity.RoundEntity
import com.szoldapps.weliwriterkmp.data.db.entity.RoundValueEntity

@Database(
    entities = [
        GithubRepoEntity::class,
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
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): GithubRepoDao

    abstract fun playerDao(): PlayerDao

    abstract fun playerGameDao(): PlayerGameDao

    abstract fun matchDao(): MatchDao

    abstract fun gameDao(): GameDao

    abstract fun roundDao(): RoundDao

    abstract fun roundValueDao(): RoundValueDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val dbFileName = "app_room_db.db"


interface DB {
    fun clearAllTables() {}
}
