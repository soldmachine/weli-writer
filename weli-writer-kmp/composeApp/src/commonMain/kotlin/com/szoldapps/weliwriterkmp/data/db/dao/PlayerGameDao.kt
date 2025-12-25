package com.szoldapps.weliwriterkmp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.szoldapps.weliwriterkmp.data.db.mapper.mapToGameEntity
import com.szoldapps.weliwriterkmp.data.db.entity.GameEntity
import com.szoldapps.weliwriterkmp.data.db.entity.GameWithPlayersEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerGameEntity
import com.szoldapps.weliwriterkmp.domain.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerGameDao {

    @Transaction
    @Query("SELECT * FROM game WHERE game_match_id=:matchId")
    fun getGamesWithPlayersEntities(matchId: Long): Flow<List<GameWithPlayersEntity>>

    @Insert
    fun insertGameEntity(gameEntity: GameEntity): Long

    @Insert
    fun insertPlayerEntities(playerEntities: List<PlayerEntity>): List<Long>

    @Insert
    fun insert(playerGameEntity: List<PlayerGameEntity>)

    @Transaction
    fun insert(game: Game, matchId: Long): Long {
        val gameId = insertGameEntity(game.mapToGameEntity(matchId))
        val playerIds = game.players.map { player -> player.id }
        val playerGameCrossRefs = playerIds.map { playerId ->
            PlayerGameEntity(
                playerId = playerId,
                gameId = gameId
            )
        }
        insert(playerGameCrossRefs)
        return gameId
    }
}
