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
    suspend fun getPlayersWithGamesEntities(): List<PlayerWithGamesEntity>

    @Query("SELECT * FROM player")
    fun getAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE player_id IN (:playerIds)")
    suspend fun loadAllByIds(playerIds: LongArray): List<PlayerEntity>

    @Query(
        "SELECT * FROM player WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    suspend fun findByName(first: String, last: String): PlayerEntity

    @Insert
    suspend fun insertAll(vararg playerEntities: PlayerEntity)

    @Insert
    suspend fun insert(playerEntities: List<PlayerEntity>): List<Long>

    @Insert
    suspend fun insertX(playerGameEntity: List<PlayerGameEntity>)

    @Delete
    suspend fun delete(playerEntity: PlayerEntity)
}
