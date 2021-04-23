package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.db.entity.GameEntity
import com.szoldapps.weli.writer.data.db.entity.PlayerEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM `game`")
    fun getAll(): LiveData<List<GameEntity>>

    @Query("SELECT * FROM `game` WHERE game_match_id=:matchId")
    fun getGamesByMatchById(matchId: Long): LiveData<List<GameEntity>>

    @Insert
    fun insertAll(vararg gameEntity: GameEntity)

    @Insert
    fun insert(gameEntity: GameEntity): Long

    @Query(
        """
        SELECT player.* FROM game 
        JOIN player_game ON game.game_id = player_game.game_id
        JOIN player ON player_game.player_id = player.player_id
        AND game.game_id = :gameId
    """
    )
    fun getPlayersOfGame(gameId: Long): List<PlayerEntity>
}
