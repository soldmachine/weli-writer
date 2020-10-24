package com.szoldapps.weli.writer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.entity.PlayerEntity

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): List<PlayerEntity>

    @Query("SELECT * FROM player WHERE player_id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<PlayerEntity>

    @Query("SELECT * FROM player WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): PlayerEntity

    @Insert
    fun insertAll(vararg playerEntities: PlayerEntity)

    @Delete
    fun delete(playerEntity: PlayerEntity)
}
