package com.szoldapps.weliwriterkmp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "player_game",
    primaryKeys = ["player_id", "game_id"]
)
data class PlayerGameEntity(
    @ColumnInfo(name = "player_id")
    var playerId: Long = 0,
    @ColumnInfo(name = "game_id")
    var gameId: Long = 0
)
