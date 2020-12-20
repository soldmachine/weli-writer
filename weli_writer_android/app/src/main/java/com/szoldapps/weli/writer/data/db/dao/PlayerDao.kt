package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.szoldapps.weli.writer.data.db.entity.PlayerEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerGameEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerWithGamesEntity

@Dao
interface PlayerDao {

    @Transaction
    @Query("SELECT * FROM player")
    fun getPlayersWithGamesEntities(): List<PlayerWithGamesEntity>

    @Query("SELECT * FROM player")
    fun getAll(): LiveData<List<PlayerEntity>>

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
