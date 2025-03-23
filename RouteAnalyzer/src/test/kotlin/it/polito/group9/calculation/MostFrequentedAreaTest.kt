package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.MostFrequentedAreaResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private const val DELTA = 0.02

class CalculationsTest {
    val parameters = CustomParameters(
        41.9028,
        12.4964,
        6371.0,
        0.1,
        0.1
    )

    // computeDefaultMostFrequentedAreaRadius
    @Test
    fun testComputeDefaultMostFrequentedAreaRadiusWhenLessThanOne() {
        assertEquals(0.1, computeDefaultMostFrequentedAreaRadius(0.5), DELTA)
    }

    @Test
    fun testComputeDefaultMostFrequentedAreaRadiusWhenGreaterThanOrEqualToOne() {
        val farthest = 10.0
        assertEquals(farthest * 0.1, computeDefaultMostFrequentedAreaRadius(farthest), DELTA)
    }

    // mostFrequentedArea
    @Test
    fun testMostFrequentedAreaEmptyList() {
        assertFailsWith<IllegalArgumentException> {
            mostFrequentedArea(emptyList(), parameters)
        }
    }

    @Test
    fun testMostFrequentedAreaSinglePoint() {
        val wp = WayPoint(123, 45.678, 9.012)
        val expectedResult = MostFrequentedAreaResult(wp, 0.1, 1)
        val result = mostFrequentedArea(listOf(wp), parameters)
        assertEquals(expectedResult, result)
    }

    @Test
    fun testMostFrequentedAreaClusteringDefaultRadius() {
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.6785, 9.0125)
        val wp3 = WayPoint(123, 45.6787, 9.0127)
        val expectedResult = MostFrequentedAreaResult(wp1, 0.1, 3)
        val result = mostFrequentedArea(listOf(wp1, wp2, wp3), parameters)
        assertEquals(expectedResult, result)
    }

    @Test
    fun testMostFrequentedAreaWithProvidedRadius() {
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.6784, 9.0124)
        val wp3 = WayPoint(123, 45.680, 9.016)
        val expectedResult = MostFrequentedAreaResult(wp1, 0.1, 2)
        val result = mostFrequentedArea(listOf(wp1, wp2, wp3), parameters)
        assertEquals(expectedResult, result)
    }
}
