package com.szoldapps.weli.writer.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "round")
data class RoundEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "round_id")
    val roundId: Long = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: OffsetDateTime,
    @ColumnInfo(name = "round_game_id")
    val gameId: Int
)
