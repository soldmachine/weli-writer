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
    fun getRoundsByGameById(gameId: Long): LiveData<List<RoundEntity>>

    @Insert
    fun insertAll(vararg roundEntity: RoundEntity)

    @Query(
        """
        SELECT * FROM player 
            WHERE player_id IN (
                SELECT player_id FROM player_game WHERE game_id = (
                    SELECT game_match_id FROM game WHERE game_id = (
                        SELECT round_game_id FROM round WHERE round_id=:roundId
                    )
                )
            )
    """
    )
    fun getPlayersOfRound(roundId: Long): List<PlayerEntity>
}
