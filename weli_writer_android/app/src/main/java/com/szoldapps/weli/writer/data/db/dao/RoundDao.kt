package com.szoldapps.weli.writer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.szoldapps.weli.writer.data.db.entity.PlayerEntity
import com.szoldapps.weli.writer.data.db.entity.RoundEntity

@Dao
interface RoundDao {

    @Query("SELECT * FROM `round`")
    fun getAll(): LiveData<List<RoundEntity>>

    @Query("SELECT * FROM `round` WHERE round_game_id=:gameId")
    fun getRoundsByGameById(gameId: Long): List<RoundEntity>

    @Query(
        """
        SELECT round_id FROM round 
        WHERE round_game_id == (SELECT round_game_id FROM round WHERE round_id = :roundId) 
        ORDER BY date_time ASC
        """
    )
    fun getListOfRoundIdsByRoundId(roundId: Long): List<Long>

    @Insert
    fun insert(roundEntity: RoundEntity): Long

    @Query(
        """
        SELECT player.* FROM game 
        JOIN player_game ON game.game_id = player_game.game_id
        JOIN player ON player_game.player_id = player.player_id
        AND game.game_id = (SELECT round_game_id FROM round WHERE round_id = :roundId)
    """
    )
    fun getPlayersOfRound(roundId: Long): List<PlayerEntity>
}
