package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.WaypointsOutsideGeofenceResult
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals

class WaypointsOutsideGeofenceTest {
    val parameters = CustomParameters(
        0.0,
        0.0,
        6371.0,
        100.0,
        0.1
    )

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should return empty list when all inside` () {
        // Arrange
        val waypoints = listOf(
            WayPoint(1, 0.0, 0.1),
            WayPoint(2, 0.1, 0.0),
            WayPoint(3, 0.1, 0.1)
        )
        val expectedResult = WaypointsOutsideGeofenceResult(
            WayPoint(0, 0.0, 0.0),
            100.0,
            0,
            emptyList()
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, parameters)

        // Assert
        assertEquals(expectedResult, result, "All waypoints should be inside the geofence")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should return all waypoints when all outside` () {
        // Arrange
        val waypoints = listOf(
            WayPoint(1, 10.0, 10.0),
            WayPoint(2, 20.0, 20.0),
            WayPoint(3, 30.0, 30.0)
        )
        val expectedResult = WaypointsOutsideGeofenceResult(
            WayPoint(0, 0.0, 0.0),
            100.0,
            waypoints.size,
            waypoints
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, parameters)

        // Assert
        assertEquals(expectedResult, result, "All waypoints should be outside the geofence")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should return correct waypoints` () {
        // Arrange
        val waypoints = listOf(
            WayPoint(1, 0.0, 0.1),
            WayPoint(2, 10.0, 10.0),
            WayPoint(3, 0.1, 0.1)
        )
        val expectedResult = WaypointsOutsideGeofenceResult(
            WayPoint(0, 0.0, 0.0),
            100.0,
            1,
            listOf(WayPoint(2, 10.0, 10.0))
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, parameters)

        // Assert
        assertEquals(expectedResult, result, "The waypoint outside the geofence should be returned")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should handle empty list` () {
        // Arrange
        val waypoints = emptyList<WayPoint>()
        val expectedResult = WaypointsOutsideGeofenceResult(
            WayPoint(0, 0.0, 0.0),
            100.0,
            0,
            emptyList()
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, parameters)

        // Assert
        assertEquals(expectedResult, result, "Result should be empty for an empty sequence")
    }
}