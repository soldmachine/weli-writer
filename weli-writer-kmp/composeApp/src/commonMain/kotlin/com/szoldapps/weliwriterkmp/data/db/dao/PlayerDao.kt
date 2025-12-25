package com.szoldapps.weliwriterkmp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerGameEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerWithGamesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Transaction
    @Query("SELECT * FROM player")
    fun getPlayersWithGamesEntities(): List<PlayerWithGamesEntity>

    @Query("SELECT * FROM player")
    fun getAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE player_id IN (:playerIds)")
    fun loadAllByIds(playerIds: LongArray): List<PlayerEntity>

    @Query(
        "SELECT * FROM player WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): PlayerEntity

    @Insert
    fun insertAll(vararg playerEntities: PlayerEntity)

    @Insert
    fun insert(playerEntities: List<PlayerEntity>): List<Long>

    @Insert
    fun insertX(playerGameEntity: List<PlayerGameEntity>)

    @Delete
    fun delete(playerEntity: PlayerEntity)
}
