package com.szoldapps.weli.writer.calculation

class CalculationRepository {

    fun calculateResult(game: Game): CalculationResult {
        val zeroRatedGameSums = getZeroRatedAndSortedSums(game)
        val payments = mutableListOf<Payment>()

        val playerCount = zeroRatedGameSums.gameSums.size
        zeroRatedGameSums.gameSums.forEachIndexed { index, roundValue ->
            val player = roundValue.player
            val playerPoints = roundValue.points

            // receives
            var receivesSum = 0
            for (innerIndex in index + 1 until playerCount) {
                val innerPoints = zeroRatedGameSums.gameSums[innerIndex].points
                receivesSum += innerPoints - playerPoints
            }

            // pays
            var paysSum = 0
            if (index != 0) {
                for (innerIndex in index downTo 0) {
                    val innerPoints = zeroRatedGameSums.gameSums[innerIndex].points
                    paysSum += playerPoints - innerPoints
                }
            }

            payments.add(
                Payment(
                    receiver = player,
                    value = (receivesSum - paysSum).toDouble()
                )
            )
        }

        return CalculationResult(
            payments = payments
        )
    }

    fun getZeroRatedAndSortedSums(game: Game): ZeroRatedGameSums {
        val playerSumMap = mutableMapOf<Player, Int>()
        game.rounds.forEach { round ->
            round.values.forEach { roundValue ->
                // enter player into map if missing
                if (playerSumMap[roundValue.player] == null) {
                    playerSumMap[roundValue.player] = 0
                }
                val oldSum = playerSumMap[roundValue.player] ?: 0
                playerSumMap[roundValue.player] = oldSum + roundValue.points
            }
        }

        val minimumSum = playerSumMap.minBy { entry ->
            entry.value
        }?.value ?: 0

        val gameSums = playerSumMap.map { entry ->
            RoundValue(
                player = entry.key,
                points = entry.value - minimumSum
            )
        }.sortedBy { roundValue -> roundValue.points }

        return ZeroRatedGameSums(gameSums)
    }


}

data class Game(
    val rounds: List<Round>
)

data class Round(
    val number: Int,
    val values: List<RoundValue>
)

data class RoundValue(
    val player: Player,
    val points: Int
)

data class Player(
    val name: String
)

data class CalculationResult(
    val payments: List<Payment>
)

data class Payment(
//    val payer: Player,
    val receiver: Player,
    val value: Double
)

data class ZeroRatedGameSums(
    val gameSums: List<RoundValue>
)
