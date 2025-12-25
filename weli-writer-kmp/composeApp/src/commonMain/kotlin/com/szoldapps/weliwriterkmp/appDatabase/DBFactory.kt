package com.szoldapps.weliwriterkmp.appDatabase

expect class DBFactory {
    fun createDatabase(): AppDatabase
}
