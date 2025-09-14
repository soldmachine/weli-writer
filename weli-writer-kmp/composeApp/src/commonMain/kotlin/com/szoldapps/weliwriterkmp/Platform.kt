package com.szoldapps.weliwriterkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform