package com.szoldapps.weli.writer.data.db.dao

import androidx.room.*
import com.szoldapps.weli.writer.data.db.entity.PlayerEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerGameEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerWithGamesEntity

@Dao
interface PlayerGameDao {

    @Insert
    fun insert(playerGameEntity: List<PlayerGameEntity>)
}
