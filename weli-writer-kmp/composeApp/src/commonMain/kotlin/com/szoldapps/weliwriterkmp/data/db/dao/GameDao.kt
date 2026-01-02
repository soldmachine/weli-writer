package com.szoldapps.weliwriterkmp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weliwriterkmp.data.db.entity.GameEntity
import com.szoldapps.weliwriterkmp.data.db.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM `game`")
    fun getAll(): Flow<List<GameEntity>>

    @Query("SELECT * FROM `game` WHERE game_match_id=:matchId")
    fun getGamesByMatchById(matchId: Long): Flow<List<GameEntity>>

    @Insert
    suspend fun insertAll(vararg gameEntity: GameEntity)

    @Insert
    suspend fun insert(gameEntity: GameEntity): Long

    @Query(
        """
        SELECT player.* FROM game 
        JOIN player_game ON game.game_id = player_game.game_id
        JOIN player ON player_game.player_id = player.player_id
        AND game.game_id = :gameId
    """
    )
    suspend fun getPlayersOfGame(gameId: Long): List<PlayerEntity>
}
