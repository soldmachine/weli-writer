package com.szoldapps.weli.writer.data.mapper

import com.szoldapps.weli.writer.data.entity.GameEntity
import com.szoldapps.weli.writer.domain.Game

fun GameEntity.mapToGame(): Game =
    Game(
        id = gameId,
        date = dateTime,
    )

fun List<GameEntity>.mapToGames(): List<Game> = this.map { gameEntity -> gameEntity.mapToGame() }

fun Game.mapToGameEntity(matchId: Int): GameEntity =
    GameEntity(
        dateTime = date,
        matchId = matchId
    )
