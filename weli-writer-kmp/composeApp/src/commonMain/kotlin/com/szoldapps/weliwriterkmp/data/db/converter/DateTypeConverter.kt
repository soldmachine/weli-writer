package com.szoldapps.weliwriterkmp.data.db.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

object DateTypeConverter {

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.toString() // ISO-8601
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let(LocalDateTime::parse)
    }
}
