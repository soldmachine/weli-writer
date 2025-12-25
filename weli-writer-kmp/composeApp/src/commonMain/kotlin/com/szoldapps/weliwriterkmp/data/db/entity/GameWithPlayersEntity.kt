package com.szoldapps.weliwriterkmp.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GameWithPlayersEntity(
    @Embedded
    val game: GameEntity,
    @Relation(
        parentColumn = "game_id",
        entityColumn = "player_id",
        associateBy = Junction(PlayerGameEntity::class)
    )
    val players: List<PlayerEntity>
)
