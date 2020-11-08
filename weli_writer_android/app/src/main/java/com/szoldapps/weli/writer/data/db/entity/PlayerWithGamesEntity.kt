package com.szoldapps.weli.writer.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlayerWithGamesEntity(
    @Embedded
    val player: PlayerEntity,
    @Relation(
        parentColumn = "player_id",
        entityColumn = "game_id",
        associateBy = Junction(PlayerGameEntity::class)
    )
    val games: List<GameEntity>
)
