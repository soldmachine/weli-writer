package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.db.entity.RoundValueEntity

@Dao
interface RoundValueDao {

    @Query("SELECT * FROM `round_value`")
    fun getAll(): LiveData<List<RoundValueEntity>>

    @Query("SELECT * FROM `round_value` WHERE round_value_round_id=:roundId")
    fun getRoundValueByRoundIdLiveData(roundId: Long): List<RoundValueEntity>

    @Query("SELECT * FROM `round_value` WHERE round_value_round_id=:roundId")
    fun getRoundValueByRoundId(roundId: Long): List<RoundValueEntity>

    @Query("SELECT COUNT(*) FROM `round_value` WHERE round_value_round_id=:roundId")
    fun getRoundValueCountByRoundId(roundId: Long): Int

    @Query("SELECT SUM(value) FROM round_value WHERE round_value_round_id = :roundId AND round_value_player_id = :playerId")
    fun sumValueOfPlayer(roundId: Long, playerId: Long): Int

    @Insert
    fun insertAll(vararg roundValueEntity: RoundValueEntity)
}
