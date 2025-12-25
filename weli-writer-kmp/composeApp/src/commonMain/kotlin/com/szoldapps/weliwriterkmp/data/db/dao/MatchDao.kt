package com.szoldapps.weliwriterkmp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weliwriterkmp.data.db.entity.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Query("SELECT * FROM `match`")
    fun getAll(): Flow<List<MatchEntity>>

    @Query("SELECT * FROM `match` WHERE match_id=:matchId")
    fun getMatchById(matchId: Int): Flow<MatchEntity>

    @Insert
    fun insertAll(vararg matchEntity: MatchEntity)

    @Delete
    fun delete(matchEntity: MatchEntity)
}
