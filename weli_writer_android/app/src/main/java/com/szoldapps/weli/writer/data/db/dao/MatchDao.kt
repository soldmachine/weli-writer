package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.db.entity.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Query("SELECT * FROM `match`")
    fun getAll(): Flow<List<MatchEntity>>

    @Query("SELECT * FROM `match` WHERE match_id=:matchId")
    fun getMatchById(matchId: Int): LiveData<MatchEntity>

    @Insert
    fun insertAll(vararg matchEntity: MatchEntity)

    @Delete
    fun delete(matchEntity: MatchEntity)
}
