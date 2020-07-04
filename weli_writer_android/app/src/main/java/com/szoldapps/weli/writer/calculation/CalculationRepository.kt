package com.szoldapps.weli.writer.calculation

import javax.inject.Inject

class CalculationRepository @Inject constructor() {

    fun calculateResult(game: Game): CalculationResult {
        val zeroRatedGameSums = getZeroRatedAndSortedSums(game).gameSums

        val balanceMap = mutableMapOf<Player, Int>()
        val playerCount = zeroRatedGameSums.size
        zeroRatedGameSums.forEachIndexed { index, roundValue ->
            val player = roundValue.player
            val playerPoints = roundValue.points

            // receives
            var receivesSum = 0
            for (innerIndex in index + 1 until playerCount) {
                val innerPoints = zeroRatedGameSums[innerIndex].points
                receivesSum += innerPoints - playerPoints
            }

            // pays
            var paysSum = 0
            if (index != 0) {
                for (innerIndex in index downTo 0) {
                    val innerPoints = zeroRatedGameSums[innerIndex].points
                    paysSum += playerPoints - innerPoints
                }
            }

            // store
            balanceMap[player] = receivesSum - paysSum
        }


        return CalculationResult(
            payments = getPaymentsFrom(balanceMap)
        )
    }

    private fun getPaymentsFrom(balanceMap: MutableMap<Player, Int>): List<Payment> {
        val positivePoints = balanceMap.filterValues { points -> points >= 0 }
        val negativePoints = balanceMap.filterValues { points -> points < 0 }.toMutableMap()

        // Sanity check
        if (positivePoints.values.sum() + negativePoints.values.sum() != 0) {
            throw IllegalStateException("Calculation of balances was wrong!")
        }

        val payments = mutableListOf<Payment>()
        positivePoints.forEach { positiveBalance ->
            var pointsToReceive = positiveBalance.value
            negativePoints.forEach { negativeBalance ->
                val pointsAvailable = negativeBalance.value
                if (pointsAvailable != 0) {
                    val paymentValue = if (pointsToReceive <= (pointsAvailable * -1)) {
                        pointsToReceive
                    } else {
                        pointsAvailable * -1
                    }
                    negativePoints[negativeBalance.key] = pointsAvailable + paymentValue
                    pointsToReceive -= paymentValue

                    payments.add(
                        Payment(
                            receiver = positiveBalance.key,
                            payer = negativeBalance.key,
                            value = paymentValue.toDouble()
                        )
                    )
                }
            }
        }

        return payments
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
    val payer: Player,
    val receiver: Player,
    val value: Double
)

data class ZeroRatedGameSums(
    val gameSums: List<RoundValue>
)
