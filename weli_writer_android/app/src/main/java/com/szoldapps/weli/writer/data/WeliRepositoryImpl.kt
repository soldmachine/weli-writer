package com.szoldapps.weli.writer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.szoldapps.weli.writer.calculation.CalculationRepository
import com.szoldapps.weli.writer.calculation.GameX
import com.szoldapps.weli.writer.calculation.PlayerX
import com.szoldapps.weli.writer.calculation.RoundValueX
import com.szoldapps.weli.writer.calculation.RoundX
import com.szoldapps.weli.writer.data.db.dao.GameDao
import com.szoldapps.weli.writer.data.db.dao.PlayerDao
import com.szoldapps.weli.writer.data.db.dao.PlayerGameDao
import com.szoldapps.weli.writer.data.db.dao.RoundDao
import com.szoldapps.weli.writer.data.db.dao.RoundValueDao
import com.szoldapps.weli.writer.data.db.mapper.mapToGames
import com.szoldapps.weli.writer.data.db.mapper.mapToPlayerEntity
import com.szoldapps.weli.writer.data.db.mapper.mapToPlayers
import com.szoldapps.weli.writer.data.db.mapper.mapToRoundEntity
import com.szoldapps.weli.writer.data.db.mapper.mapToRoundRowValues
import com.szoldapps.weli.writer.data.db.mapper.mapToRoundValueEntity
import com.szoldapps.weli.writer.data.db.mapper.mapToRounds
import com.szoldapps.weli.writer.data.extensions.mapToInitials
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.GameRvAdapterItem
import com.szoldapps.weli.writer.domain.GameRvAdapterItem.GameRowHeader
import com.szoldapps.weli.writer.domain.GameRvAdapterItem.GameRowSummation
import com.szoldapps.weli.writer.domain.GameRvAdapterItem.GameRowValues
import com.szoldapps.weli.writer.domain.Player
import com.szoldapps.weli.writer.domain.Round
import com.szoldapps.weli.writer.domain.RoundValue
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowHeader
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowValues
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.WeliConstants.WELI_MAX_ROUNDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeliRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao,
    private val playerGameDao: PlayerGameDao,
    private val gameDao: GameDao,
    private val roundDao: RoundDao,
    private val roundValueDao: RoundValueDao,
    private val calculationRepository: CalculationRepository,
) : WeliRepository {

    override val players: LiveData<List<Player>> = playerDao.getAll().map { it.mapToPlayers() }

    override fun gamesByMatchId(matchId: Long): LiveData<List<Game>> =
        playerGameDao.getGamesWithPlayersEntities(matchId).map { gamesWithPlayers ->
            gamesWithPlayers.mapToGames()
        }

    override suspend fun gameRvAdapterItemsByGameId(gameId: Long): List<GameRvAdapterItem> =
        withContext(Dispatchers.IO) {
            val roundEntities = roundDao.getRoundsByGameById(gameId)
            val rounds = roundEntities.mapToRounds()
            val gameRowValues = rounds.mapIndexed { index, round ->
                val lastRoundRowValues = lastRoundRowValuesByRoundId(round.id)
                GameRowValues(
                    id = round.id,
                    number = index,
                    values = lastRoundRowValues.values
                )
            }
            val playersOfGame = gameDao.getPlayersOfGame(gameId).mapToPlayers()

            val header = mutableListOf<GameRvAdapterItem>(
                GameRowHeader(playersOfGame.mapToInitials())
            )

            val summation = calculateGameRowSummation(playersOfGame, gameRowValues)

            return@withContext header + gameRowValues + summation
        }

    private fun lastRoundRowValuesByRoundId(roundId: Long): RoundRowValues =
        roundValueDao.getRoundValueByRoundId(roundId)
            .mapToRoundRowValues()
            .last()

    private fun calculateGameRowSummation(
        playersOfGame: List<Player>,
        gameRowValues: List<GameRowValues>
    ): GameRowSummation {
        val roundsX = gameRowValues.map { gameRowValue ->
            RoundX(number = gameRowValue.number, values = gameRowValue.values.mapToRoundValueX(playersOfGame))
        }
        val gameX = GameX(roundsX)
        val result = calculationRepository.calculateResult(gameX)
        return GameRowSummation(result.toString())
    }

    private fun List<Int>.mapToRoundValueX(playersOfGame: List<Player>): List<RoundValueX> =
        this.mapIndexed { index, value ->
            RoundValueX(
                player = PlayerX(playersOfGame[index].firstName + " " + playersOfGame[index].lastName),
                points = value,
            )
        }

    override suspend fun roundValueRvAdapterItemsByRoundId(
        roundId: Long,
        onButtonClickListener: () -> (Unit)
    ): List<RoundValueRvAdapterItem> = withContext(Dispatchers.IO) {
        val roundRowValues = roundValueDao.getRoundValueByRoundIdLiveData(roundId).mapToRoundRowValues()

        val list = mutableListOf<RoundValueRvAdapterItem>(RoundRowHeader(getPlayerInitialsOfRound(roundId)))
        list.addAll(roundRowValues)
        if (roundRowValues.hasRoundsToPlay() && roundRowValues.doNotContainWinner()) {
            list.add(
                RoundValueRvAdapterItem.RoundRowButton(
                    label = "Add round result",
                    action = { onButtonClickListener.invoke() }
                )
            )
        }
        return@withContext list
    }

    private fun List<RoundRowValues>.hasRoundsToPlay(): Boolean = this.count() - 1 < WELI_MAX_ROUNDS

    private fun List<RoundRowValues>.doNotContainWinner(): Boolean = !last().values.contains(0)

    override suspend fun roundValueCountByRoundId(roundId: Long): Int = withContext(Dispatchers.IO) {
        roundValueDao.getRoundValueCountByRoundId(roundId) / 4
    }

    override suspend fun addGame(game: Game, matchId: Long) = withContext(Dispatchers.IO) {
        playerGameDao.insert(game, matchId)
    }

    override suspend fun addRound(round: Round, gameId: Long): Long = withContext(Dispatchers.IO) {
        roundDao.insert(round.mapToRoundEntity(gameId))
    }

    /**
     * Adds a RoundValue but makes sure that the minimum value is 0, i.e. the sum of a player can't become negative
     */
    override suspend fun addRoundValue(
        roundValue: RoundValue,
        roundId: Long,
        player: Player
    ) = withContext(Dispatchers.IO) {
        val sumValueOfPlayerBeforeInsert = roundValueDao.sumValueOfPlayer(roundId = roundId, playerId = player.id)
        val sumValueAfterInsert = sumValueOfPlayerBeforeInsert + roundValue.value
        val roundValueToInsert = if (sumValueAfterInsert < 0) {
            roundValue.copy(value = -sumValueOfPlayerBeforeInsert)
        } else {
            roundValue
        }
        roundValueDao.insertAll(roundValueToInsert.mapToRoundValueEntity(roundId, player.id))
    }

    override suspend fun addPlayer(player: Player) = withContext(Dispatchers.IO) {
        playerDao.insertAll(player.mapToPlayerEntity())
    }

    override suspend fun getPlayersOfRound(roundId: Long): List<Player> = withContext(Dispatchers.IO) {
        roundDao.getPlayersOfRound(roundId).mapToPlayers()
    }

    override suspend fun getPlayerInitialsOfRound(roundId: Long): List<String> = withContext(Dispatchers.IO) {
        roundDao.getPlayersOfRound(roundId).mapToPlayers().mapToInitials()
    }

    override suspend fun getTricksByRoundIdAndNumber(roundId: Long, roundNumber: Int): List<Int> =
        withContext(Dispatchers.IO) {
            roundValueDao.getRoundValueByRoundIdAndNumber(roundId, roundNumber).map { roundValue -> roundValue.value }
        }

    override suspend fun updateRoundValues(roundId: Long, roundNumber: Int, tricks: List<Int>) {
        withContext(Dispatchers.IO) {
            roundValueDao.getRoundValueByRoundIdAndNumber(roundId, roundNumber)
                .mapIndexed { index, entity -> entity.copy(value = tricks[index]) }
                .forEach {
                    roundValueDao.insertAll(it)
                }

            getPlayersOfRound(roundId).forEach { player ->
                val currentVal = roundValueDao.sumValueOfPlayer(roundId, player.id)

                if (currentVal < 0) {
                    val roundVal = roundValueDao.getRoundValueByRoundIdAndNumber(roundId, roundNumber).find { roundValue -> roundValue.playerId == player.id }
                    roundVal?.copy(value = roundVal.value - currentVal)?.let { roundValueDao.insertAll(it) }
                }
            }
        }
    }

    override suspend fun getIndexOfRoundInGameByRoundId(roundId: Long): Int = withContext(Dispatchers.IO) {
        val rounds = roundDao.getListOfRoundIdsByRoundId(roundId)
        rounds.indexOf(roundId)
    }
}
