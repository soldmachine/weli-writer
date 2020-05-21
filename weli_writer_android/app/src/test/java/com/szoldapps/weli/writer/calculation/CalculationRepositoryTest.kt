package com.szoldapps.weli.writer.calculation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CalculationRepositoryTest {

    private val calculationRepository = CalculationRepository()

    @Test
    fun `given generated game 1, correct zero rated game sums are created`() {
        // given
        val game = generateGame()
        val expectedZeroRatedGameSums = generateZeroRatedGameSums()

        // when
        val actualZeroRatedGameSums = calculationRepository.getZeroRatedAndSortedSums(game)

        // then
        assertEquals(expectedZeroRatedGameSums, actualZeroRatedGameSums)
    }

    @Test
    fun `given generated game 1, correct result is calculated`() {
        // given
        val game = generateGame()
        val expectedCalculationResult = generateCalculationResult()

        // when
        val actualCalculationResult = calculationRepository.calculateResult(game)

        // then
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
