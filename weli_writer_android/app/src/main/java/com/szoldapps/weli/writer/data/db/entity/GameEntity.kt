package com.szoldapps.weli.writer.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val gameId: Long = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: OffsetDateTime,
    @ColumnInfo(name = "game_match_id")
    val matchId: Long
)
