package com.szoldapps.weli.writer.data.db.mapper

import com.szoldapps.weli.writer.data.db.entity.GameEntity
import com.szoldapps.weli.writer.data.db.entity.GameWithPlayersEntity
import com.szoldapps.weli.writer.domain.Game

fun List<GameWithPlayersEntity>.mapToGames(): List<Game> =
    this.map { gameWithPlayersEntity -> gameWithPlayersEntity.mapToGame() }

fun GameWithPlayersEntity.mapToGame() =
    Game(
        id = game.gameId,
        date = game.dateTime,
        players = players.mapToPlayers()
    )

fun Game.mapToGameEntity(matchId: Long): GameEntity =
    GameEntity(
        dateTime = date,
        matchId = matchId
    )
