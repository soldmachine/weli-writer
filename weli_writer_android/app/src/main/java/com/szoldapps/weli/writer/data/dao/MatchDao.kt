package com.szoldapps.weli.writer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.entity.MatchEntity

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match`")
    fun getAll(): List<MatchEntity>

    @Insert
    fun insertAll(vararg matchEntity: MatchEntity)

    @Delete
    fun delete(matchEntity: MatchEntity)
}
