package com.szoldapps.weli.writer.calculation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CalculationRepositoryTest {

    private val calculationRepository = CalculationRepository()

    @Test
    fun `given generated game 1, correct zero rated game sums are created`() {
        // given
        val game = generateGame1()
        val expectedZeroRatedGameSums = generateZeroRatedGameSums()

        // when
        val actualZeroRatedGameSums = calculationRepository.getZeroRatedAndSortedSums(game)

        // then
        assertEquals(expectedZeroRatedGameSums, actualZeroRatedGameSums)
    }

    @Test
    fun `given generated game 1, correct result is calculated`() {
        // given
        val game = generateGame1()
        val expectedCalculationResult = generateCalculationResult1()

        // when
        val actualCalculationResult = calculationRepository.calculateResult(game)

        // then
        print(actualCalculationResult)
        assertEquals(expectedCalculationResult, actualCalculationResult)
    }

    @Test
    fun `given generated game 2, correct result is calculated`() {
        // given
        val game = generateGame2()
        val expectedCalculationResult = generateCalculationResult2()

        // when
        val actualCalculationResult = calculationRepository.calculateResult(game)

        // then
        print(actualCalculationResult)
        assertEquals(expectedCalculationResult, actualCalculationResult)
    }


//    @Test
//    fun calculateResult() {
//        // given
//        val game = generateGame()
//        val expectedCalculationResult = CalculationResult()
//
//        // when
//        val actualCalculationResult = calculationRepository.calculateResult(game)
//
//        // then
//        assertEquals(expectedCalculationResult, actualCalculationResult)
//    }
}
