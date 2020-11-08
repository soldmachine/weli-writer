package com.szoldapps.weli.writer.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "player_game",
    primaryKeys = ["player_id", "game_id"]
)
data class PlayerGameCrossRef(
    @ColumnInfo(name = "player_id")
    val playerId: Long = 0,
    @ColumnInfo(name = "game_id")
    val gameId: Long = 0
)
