package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.db.entity.RoundEntity

@Dao
interface RoundDao {

    @Query("SELECT * FROM `round`")
    fun getAll(): LiveData<List<RoundEntity>>

    @Query("SELECT * FROM `round` WHERE round_game_id=:gameId")
    fun getRoundsByGameById(gameId: Long): LiveData<List<RoundEntity>>

    @Insert
    fun insertAll(vararg roundEntity: RoundEntity)
}
