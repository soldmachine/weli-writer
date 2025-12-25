package com.szoldapps.weliwriterkmp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "round_value")
data class RoundValueEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "round_value_id")
    val roundValueId: Long = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: LocalDateTime,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "value")
    val value: Int,
    @ColumnInfo(name = "round_value_round_id")
    val roundId: Long,
    @ColumnInfo(name = "round_value_player_id")
    val playerId: Long
)
