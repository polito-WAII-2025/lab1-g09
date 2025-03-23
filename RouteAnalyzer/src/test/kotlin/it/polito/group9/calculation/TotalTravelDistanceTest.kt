package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.utils.distanceBetweenWayPoints
import kotlin.test.Test
import kotlin.test.assertEquals

class TotalTravelDistanceTest {
    val parameters = CustomParameters(
        41.9028,
        12.4964,
        6371.0,
        0.1,
        0.1
    )

    @Test
    fun totalTravelDistance_withMultipleWaypoints_returnsCorrectDistance() {
        val waypoints = listOf(
            WayPoint(1L, 45.0, 7.0),
            WayPoint(2L, 46.0, 8.0),
            WayPoint(3L, 47.0, 9.0)
        )
        val result = totalTravelDistance(waypoints, parameters)
        val expectedDistance = distanceBetweenWayPoints(waypoints[0], waypoints[1], 6371.0) +
                distanceBetweenWayPoints(waypoints[1], waypoints[2], 6371.0)
        assertEquals(expectedDistance, result.totalDistance)
        assertEquals(3, result.entriesCount)
    }

    @Test
    fun totalTravelDistance_withSingleWaypoint_returnsZeroDistance() {
        val waypoints = listOf(WayPoint(1L, 45.0, 7.0))
        val result = totalTravelDistance(waypoints, parameters)
        assertEquals(0.0, result.totalDistance)
        assertEquals(1, result.entriesCount)
    }

    @Test
    fun totalTravelDistance_withNoWaypoints_returnsZeroDistance() {
        val waypoints = emptyList<WayPoint>()
        val result = totalTravelDistance(waypoints, parameters)
        assertEquals(0.0, result.totalDistance)
        assertEquals(0, result.entriesCount)
    }
}