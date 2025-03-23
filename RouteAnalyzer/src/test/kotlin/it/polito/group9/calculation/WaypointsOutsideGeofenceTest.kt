package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WaypointsOutsideGeofenceTest {
    private val parameters = CustomParameters()

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should return empty list when all inside` () {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = listOf(
            WayPoint(1, 0.0, 0.1),
            WayPoint(2, 0.1, 0.0),
            WayPoint(3, 0.1, 0.1)
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0, parameters)

        // Assert
        assertTrue(result.isEmpty(), "All waypoints should be inside the geofence")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should return all waypoints when all outside` () {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = listOf(
            WayPoint(1, 10.0, 10.0),
            WayPoint(2, 20.0, 20.0),
            WayPoint(3, 30.0, 30.0)
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0, parameters)

        // Assert
        assertEquals(waypoints, result, "All waypoints should be outside the geofence")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should return correct waypoints` () {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = listOf(
            WayPoint(1, 0.0, 0.1),
            WayPoint(2, 10.0, 10.0),
            WayPoint(3, 0.1, 0.1)
        )
        val expectedResult = listOf(WayPoint(2, 10.0, 10.0))

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0, parameters)

        // Assert
        assertEquals(expectedResult, result, "The waypoint outside the geofence should be returned")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun `waypointsOutsideGeofence should handle empty list` () {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = emptyList<WayPoint>()

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0, parameters)

        // Assert
        assertTrue(result.isEmpty(), "Result should be empty for an empty sequence")
    }
}