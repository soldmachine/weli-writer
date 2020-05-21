package com.szoldapps.weli.writer.calculation

private val player1 = Player("K")
private val player2 = Player("M")
private val player3 = Player("S")
private val player4 = Player("W")

@Suppress("UNUSED_CHANGED_VALUE")
internal fun generateGame(): Game {
    var number = 1
    return Game(
        rounds = listOf(
            generateRound(number++, listOf(44, 31, 26, 0)),
            generateRound(number++, listOf(29, 68, 0, 51)),
            generateRound(number++, listOf(22, 64, 0, 23)),
            generateRound(number++, listOf(0, 46, 38, 23)),
            generateRound(number++, listOf(0, 57, 53, 45)),
            generateRound(number++, listOf(0, 42, 42, 37)),
            generateRound(number++, listOf(11, 0, 25, 25)),
            generateRound(number++, listOf(0, 43, 24, 21))
        )
    )
}

private fun generateRound(number: Int, points: List<Int>): Round {
    return Round(
        number = number,
        values = listOf(
            RoundValue(
                player = player1,
                points = points[0]
            ),
            RoundValue(
                player = player2,
                points = points[1]
            ),
            RoundValue(
                player = player3,
                points = points[2]
            ),
            RoundValue(
                player = player4,
                points = points[3]
            )
        )
    )
}

fun generateZeroRatedGameSums() =
    ZeroRatedGameSums(
        gameSums = listOf(
            RoundValue(player1, 0),
            RoundValue(player3, 102),
            RoundValue(player4, 119),
            RoundValue(player2, 245)
        )
    )

fun generateCalculationResult() =
    CalculationResult(
        payments = listOf(
            Payment(player1, 466.0),
            Payment(player3, 58.0),
            Payment(player4, -10.0),
            Payment(player2, -514.0)

        )
    )
