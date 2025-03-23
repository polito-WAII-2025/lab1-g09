package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.utils.distanceBetweenWayPoints
import kotlin.test.Test
import kotlin.test.assertEquals

class AverageSpeedTest {
    val parameters = CustomParameters(
        41.9028,
        12.4964,
        6371.0,
        0.1,
        0.1
    )

    @Test
    fun averageSpeed_withMultipleWaypoints_returnsCorrectAverageSpeed() {
        val waypoints = listOf(
            WayPoint(0L, 45.0, 7.0),
            WayPoint(3600000L, 46.0, 8.0), // 1 hour later
            WayPoint(7200000L, 47.0, 9.0)  // 2 hours later
        )
        val result = averageSpeed(waypoints, parameters)
        val expectedDistance = distanceBetweenWayPoints(waypoints[0], waypoints[1], 6371.0) +
                distanceBetweenWayPoints(waypoints[1], waypoints[2], 6371.0)
        val expectedTime = 2.0 // 2 hours
        val expectedAverageSpeed = expectedDistance / expectedTime
        assertEquals(expectedAverageSpeed, result.averageSpeed)
        assertEquals(expectedDistance, result.totalDistance)
        assertEquals(expectedTime, result.totalTime)
        assertEquals(3, result.entriesCount)
    }

    @Test
    fun averageSpeed_withSingleWaypoint_returnsZeroAverageSpeed() {
        val waypoints = listOf(WayPoint(0L, 45.0, 7.0))
        val result = averageSpeed(waypoints, parameters)
        assertEquals(0.0, result.averageSpeed)
        assertEquals(0.0, result.totalDistance)
        assertEquals(0.0, result.totalTime)
        assertEquals(1, result.entriesCount)
    }

    @Test
    fun averageSpeed_withNoWaypoints_returnsZeroAverageSpeed() {
        val waypoints = emptyList<WayPoint>()
        val result = averageSpeed(waypoints, parameters)
        assertEquals(0.0, result.averageSpeed)
        assertEquals(0.0, result.totalDistance)
        assertEquals(0.0, result.totalTime)
        assertEquals(0, result.entriesCount)
    }
}