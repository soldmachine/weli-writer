package com.szoldapps.weli.writer.calculation

private val playerK = Player("K")
private val playerM = Player("M")
private val playerS = Player("S")
private val playerW = Player("W")
private val playerE = Player("E")
private val playerP = Player("P")

@Suppress("UNUSED_CHANGED_VALUE")
internal fun generateGame1(): Game {
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

fun generateCalculationResult1() =
    CalculationResult(
        payments = listOf(
            Payment(receiver = playerK, payer = playerW, value = 10.0),
            Payment(receiver = playerK, payer = playerM, value = 456.0),
            Payment(receiver = playerS, payer = playerM, value = 58.0)
        )
    )

@Suppress("UNUSED_CHANGED_VALUE")
internal fun generateGame2(): Game {
    var number = 1
    return Game(
        rounds = listOf(
            generateRound(number++, listOf(44, 31, 26, 0))
        )
    )
}

fun generateCalculationResult2() =
    CalculationResult(
        payments = listOf(
            Payment(receiver = playerW, payer = playerS, value = 3.0),
            Payment(receiver = playerW, payer = playerM, value = 23.0),
            Payment(receiver = playerW, payer = playerK, value = 75.0)
        )
    )

internal fun generateGame3(): Game {
    var number = 1
    return Game(
        rounds = listOf(
            generateRound06072020(number++, listOf(14, 0, 35, 21)),
            generateRound06072020(number++, listOf(33, 0, 16, 35)),
            generateRound06072020(number++, listOf(35, 95, 30, 0)),
            generateRound06072020(number++, listOf(0, 36, 20, 8)),
            generateRound06072020(number++, listOf(34, 0, 43, 80)),
            generateRound06072020(number++, listOf(40, 0, 54, 41)),
            generateRound06072020(number++, listOf(39, 0, 6, 33)),
            generateRound06072020(number++, listOf(0, 32, 26, 36))
        )
    )
}

internal fun generateGame4(): Game {
    var number = 1
    return Game(
        rounds = listOf(
            generateRound18072020(number++, listOf(2, 55, 44, 0)),
            generateRound18072020(number++, listOf(0, 33, 33, 44)),
            generateRound18072020(number++, listOf(32, 17, 46, 0)),
            generateRound18072020(number++, listOf(0, 24, 46, 8)),
            generateRound18072020(number++, listOf(85, 70, 11, 0)),
            generateRound18072020(number++, listOf(75, 80, 0, 42)),
            generateRound18072020(number++, listOf(17, 62, 20, 0)),
            generateRound18072020(number++, listOf(14, 6, 69, 0)),
            generateRound18072020(number++, listOf(0, 73, 61, 93)),
            generateRound18072020(number++, listOf(19, 0, 44, 27)),
            generateRound18072020(number++, listOf(46, 0, 24, 10))
        )
    )
}

internal fun generateGame5(): Game {
    var number = 1
    return Game(
        rounds = listOf(
            generateRound07082020(number++, listOf(30, 69, 0, 12)),
            generateRound07082020(number++, listOf(19, 47, 0, 23)),
            generateRound07082020(number++, listOf(28, 22, 56, 0)),
            generateRound07082020(number++, listOf(0, 21, 114, 31)),
            generateRound07082020(number++, listOf(23, 0, 101, 3)),
            generateRound07082020(number++, listOf(19, 0, 2, 22)),
            generateRound07082020(number++, listOf(27, 23, 0, 15)),
            generateRound07082020(number++, listOf(25, 0, 26, 24)),
            generateRound07082020(number++, listOf(7, 13, 33, 0))
        )
    )
}

fun generateCalculationResult3() =
    CalculationResult(
        payments = listOf(
            Payment(receiver = playerW, payer = playerS, value = 3.0),
            Payment(receiver = playerW, payer = playerM, value = 23.0),
            Payment(receiver = playerW, payer = playerK, value = 75.0)
        )
    )

fun generateCalculationResult4() =
    CalculationResult(
        payments = listOf(
            Payment(receiver = playerS, payer = playerE, value = 260.0),
            Payment(receiver = playerS, payer = playerK, value = 176.0),
            Payment(receiver = playerM, payer = playerK, value = 172.0)
        )
    )

fun generateCalculationResult5() =
    CalculationResult(
        payments = listOf(
            Payment(receiver = playerW, payer = playerS, value = 315.0),
            Payment(receiver = playerK, payer = playerS, value = 123.0),
            Payment(receiver = playerP, payer = playerS, value = 55.0)
        )
    )

private fun generateRound(number: Int, points: List<Int>): Round {
    return Round(
        number = number,
        values = listOf(
            RoundValue(
                player = playerK,
                points = points[0]
            ),
            RoundValue(
                player = playerM,
                points = points[1]
            ),
            RoundValue(
                player = playerS,
                points = points[2]
            ),
            RoundValue(
                player = playerW,
                points = points[3]
            )
        )
    )
}

private fun generateRound06072020(number: Int, points: List<Int>): Round {
    return Round(
        number = number,
        values = listOf(
            RoundValue(
                player = playerM,
                points = points[0]
            ),
            RoundValue(
                player = playerS,
                points = points[1]
            ),
            RoundValue(
                player = playerK,
                points = points[2]
            ),
            RoundValue(
                player = playerW,
                points = points[3]
            )
        )
    )
}

private fun generateRound18072020(number: Int, points: List<Int>): Round {
    return Round(
        number = number,
        values = listOf(
            RoundValue(
                player = playerM,
                points = points[0]
            ),
            RoundValue(
                player = playerK,
                points = points[1]
            ),
            RoundValue(
                player = playerE,
                points = points[2]
            ),
            RoundValue(
                player = playerS,
                points = points[3]
            )
        )
    )
}

private fun generateRound07082020(number: Int, points: List<Int>): Round {
    return Round(
        number = number,
        values = listOf(
            RoundValue(
                player = playerK,
                points = points[0]
            ),
            RoundValue(
                player = playerP,
                points = points[1]
            ),
            RoundValue(
                player = playerS,
                points = points[2]
            ),
            RoundValue(
                player = playerW,
                points = points[3]
            )
        )
    )
}

fun generateZeroRatedGameSums() =
    ZeroRatedGameSums(
        gameSums = listOf(
            RoundValue(playerK, 0),
            RoundValue(playerS, 102),
            RoundValue(playerW, 119),
            RoundValue(playerM, 245)
        )
    )
