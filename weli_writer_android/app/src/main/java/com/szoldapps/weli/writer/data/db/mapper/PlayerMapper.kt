package com.szoldapps.weli.writer.data.db.mapper

import com.szoldapps.weli.writer.data.db.entity.PlayerEntity
import com.szoldapps.weli.writer.domain.Player

fun PlayerEntity.mapToPlayer(): Player =
    Player(
        id = playerId,
        firstName = firstName,
        lastName = lastName,
    )

fun List<PlayerEntity>.mapToPlayers(): List<Player> = this.map { gameEntity -> gameEntity.mapToPlayer() }

fun Player.mapToPlayerEntity(): PlayerEntity =
    PlayerEntity(
        firstName = firstName,
        lastName = lastName
    )

fun List<Player>.mapToPlayerEntities(): List<PlayerEntity> = this.map { player -> player.mapToPlayerEntity() }

