package com.szoldapps.weliwriterkmp.data.db.setup

expect class DBFactory {
    fun createDatabase(): AppDatabase
}
