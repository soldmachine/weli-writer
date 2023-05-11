package com.szoldapps.kmp.weliwriter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform