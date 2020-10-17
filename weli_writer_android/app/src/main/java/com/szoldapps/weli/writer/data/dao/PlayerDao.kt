package com.szoldapps.weli.writer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.entity.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): List<Player>

    @Query("SELECT * FROM player WHERE player_id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<Player>

    @Query("SELECT * FROM player WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Player

    @Insert
    fun insertAll(vararg players: Player)

    @Delete
    fun delete(player: Player)
}