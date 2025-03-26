package it.polito.group9.calculation.advanced

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import kotlin.test.Test
import kotlin.test.assertEquals

class AverageWaypointsDistanceTest {
    val parameters = CustomParameters(
        41.9028,
        12.4964,
        6371.0,
        0.1,
        0.1
    )

    @Test
    fun averageWaypointsDistance_withMultipleWaypoints_returnsCorrectAverageDistance() {
        val waypoints = listOf(
            WayPoint(0L, 45.0, 7.0),
            WayPoint(3600000L, 46.0, 8.0), // 1 hour later
            WayPoint(7200000L, 47.0, 9.0)  // 2 hours later
        )
        val result = averageWaypointsDistance(waypoints, parameters)
        val expectedDistance = totalTravelDistance(waypoints, parameters).totalDistance / waypoints.size
        assertEquals(expectedDistance, result.averageDistance)
        assertEquals(waypoints.size, result.entriesCount)
    }

    @Test
    fun averageWaypointsDistance_withSingleWaypoint_returnsZeroAverageDistance() {
        val waypoints = listOf(WayPoint(0L, 45.0, 7.0))
        val result = averageWaypointsDistance(waypoints, parameters)
        assertEquals(0.0, result.averageDistance)
        assertEquals(1, result.entriesCount)
    }

    @Test
    fun averageWaypointsDistance_withNoWaypoints_returnsZeroAverageDistance() {
        val waypoints = emptyList<WayPoint>()
        val result = averageWaypointsDistance(waypoints, parameters)
        assertEquals(0.0, result.averageDistance)
        assertEquals(0, result.entriesCount)
    }
}