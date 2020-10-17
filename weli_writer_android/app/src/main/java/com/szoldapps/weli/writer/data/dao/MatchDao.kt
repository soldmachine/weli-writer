package com.szoldapps.weli.writer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.entity.MatchDb

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match`")
    fun getAll(): List<MatchDb>

    @Insert
    fun insertAll(vararg matchDb: MatchDb)

    @Delete
    fun delete(matchDb: MatchDb)
}
