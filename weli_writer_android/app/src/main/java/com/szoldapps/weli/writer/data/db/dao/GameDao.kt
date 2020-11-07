package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.szoldapps.weli.writer.data.db.entity.GameEntity
import com.szoldapps.weli.writer.data.db.entity.MatchEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM `game`")
    fun getAll(): LiveData<List<GameEntity>>

    @Query("SELECT * FROM `game` WHERE game_match_id=:matchId")
    fun getGamesByMatchById(matchId: Int): LiveData<List<GameEntity>>

    @Insert
    fun insertAll(vararg gameEntity: GameEntity)

}
