package com.szoldapps.weli.writer.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.entity.RoundValueEntity

@Dao
interface RoundValueDao {

    @Query("SELECT * FROM `round_value`")
    fun getAll(): LiveData<List<RoundValueEntity>>

    @Query("SELECT * FROM `round_value` WHERE round_value_round_id=:roundId")
    fun getRoundValueByRoundId(roundId: Long): LiveData<List<RoundValueEntity>>

    @Insert
    fun insertAll(vararg roundValueEntity: RoundValueEntity)
}
