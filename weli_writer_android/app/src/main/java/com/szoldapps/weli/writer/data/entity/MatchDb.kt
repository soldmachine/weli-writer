package com.szoldapps.weli.writer.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "match")
data class MatchDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "match_id")
    val matchId: Int = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: OffsetDateTime,
    @ColumnInfo(name = "location")
    val location: String
)
