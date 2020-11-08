package com.szoldapps.weli.writer.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "match")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "match_id")
    val matchId: Long = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: OffsetDateTime,
    @ColumnInfo(name = "location")
    val location: String
)
