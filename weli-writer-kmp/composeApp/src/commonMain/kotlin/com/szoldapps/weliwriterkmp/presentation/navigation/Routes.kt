package com.szoldapps.weliwriterkmp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
data object MatchListRoute : NavKey

@Serializable
data class MatchDetailRoute(val matchId: Long) : NavKey

internal val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MatchListRoute::class, MatchListRoute.serializer())
            subclass(MatchDetailRoute::class, MatchDetailRoute.serializer())
        }
    }
}
