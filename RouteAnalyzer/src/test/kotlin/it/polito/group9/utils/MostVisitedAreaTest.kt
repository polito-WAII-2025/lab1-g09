package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.utils.calculateEarthDistanceHaversine
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private const val DELTA = 0.02

class CalculationsTest {

    // haversineDistance

    @Test
    fun testHaversineDistanceZero() {
        val wp = WayPoint(123, 45.678, 9.012)
        assertEquals(0.0, calculateEarthDistanceHaversine(wp, wp), DELTA)
    }

    @Test
    fun testHaversineDistanceSlightLatitudeDifference() {
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.679, 9.012)
        // ≈0.111 km per 0.001° latitude
        assertEquals(0.111, calculateEarthDistanceHaversine(wp1, wp2), DELTA)
    }



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
        val params = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.5
        }
        assertFailsWith<IllegalArgumentException> {
            mostFrequentedArea(emptyList(), params)
        }
    }

    @Test
    fun testMostFrequentedAreaSinglePoint() {
        val wp = WayPoint(123, 45.678, 9.012)
        val params = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.5
        }
        val (central, count) = mostFrequentedArea(listOf(wp), params)
        assertEquals(wp, central)
        assertEquals(1, count)
    }

    @Test
    fun testMostFrequentedAreaClusteringDefaultRadius() {
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.6785, 9.0125)
        val wp3 = WayPoint(123, 45.6787, 9.0127)
        val params = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = null
        }
        val (central, count) = mostFrequentedArea(listOf(wp1, wp2, wp3), params)
        assertEquals(3, count)
        assertEquals(wp1, central)
    }

    @Test
    fun testMostFrequentedAreaWithProvidedRadius() {
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.6784, 9.0124)
        val wp3 = WayPoint(123, 45.680, 9.016)
        val params = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.2
        }
        val (central, count) = mostFrequentedArea(listOf(wp1, wp2, wp3), params)
        assertEquals(2, count)
        assertEquals(wp1, central)
    }
}
