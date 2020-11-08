package com.szoldapps.weli.writer.data.db.dao

import androidx.room.*
import com.szoldapps.weli.writer.data.db.entity.PlayerEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerWithGamesEntity

@Dao
interface PlayerDao {

    @Transaction
    @Query("SELECT * FROM player")
    fun getPlayersWithGamesEntities(): List<PlayerWithGamesEntity>

    @Query("SELECT * FROM player")
    fun getAll(): List<PlayerEntity>

    @Query("SELECT * FROM player WHERE player_id IN (:playerIds)")
    fun loadAllByIds(playerIds: LongArray): List<PlayerEntity>

    @Query(
        "SELECT * FROM player WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): PlayerEntity

    @Insert
    fun insertAll(vararg playerEntities: PlayerEntity)

    @Delete
    fun delete(playerEntity: PlayerEntity)
}
