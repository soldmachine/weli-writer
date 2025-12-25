package com.szoldapps.weliwriterkmp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "round")
data class RoundEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "round_id")
    val roundId: Long = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: LocalDateTime,
    @ColumnInfo(name = "round_game_id")
    val gameId: Long
)
