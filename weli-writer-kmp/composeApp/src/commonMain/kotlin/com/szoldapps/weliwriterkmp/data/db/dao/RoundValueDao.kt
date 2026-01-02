package com.szoldapps.weliwriterkmp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.szoldapps.weliwriterkmp.data.db.entity.RoundValueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoundValueDao {

    @Query("SELECT * FROM round_value")
    fun getAll(): Flow<List<RoundValueEntity>>

    @Query("SELECT * FROM round_value WHERE round_value_round_id = :roundId")
    suspend fun getRoundValueByRoundIdLiveData(roundId: Long): List<RoundValueEntity>

    @Query("SELECT * FROM round_value WHERE round_value_round_id = :roundId")
    suspend fun getRoundValueByRoundId(roundId: Long): List<RoundValueEntity>

    @Query("SELECT * FROM round_value WHERE round_value_round_id = :roundId AND number = :roundNumber")
    suspend fun getRoundValueByRoundIdAndNumber(roundId: Long, roundNumber: Int): List<RoundValueEntity>

    @Query("SELECT COUNT(*) FROM round_value WHERE round_value_round_id = :roundId")
    suspend fun getRoundValueCountByRoundId(roundId: Long): Int

    @Query("SELECT SUM(value) FROM round_value WHERE round_value_round_id = :roundId AND round_value_player_id = :playerId")
    suspend fun sumValueOfPlayer(roundId: Long, playerId: Long): Int

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg roundValueEntity: RoundValueEntity)
}
