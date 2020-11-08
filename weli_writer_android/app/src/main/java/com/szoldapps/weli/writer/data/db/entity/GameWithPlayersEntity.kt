package com.szoldapps.weli.writer.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GameWithPlayersEntity(
    @Embedded
    val game: GameEntity,
    @Relation(
        parentColumn = "game_id",
        entityColumn = "player_id",
        associateBy = Junction(PlayerGameCrossRef::class)
    )
    val players: List<PlayerEntity>
)
