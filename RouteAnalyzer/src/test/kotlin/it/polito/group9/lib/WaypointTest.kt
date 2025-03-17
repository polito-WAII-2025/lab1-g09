package it.polito.group9.lib

import it.polito.group9.model.WayPoint
import org.junit.jupiter.api.Tag
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WaypointTest {
    @Tag("normalizeTimestamp")
    @Test
    fun normalizeTimestampShouldReturnTimestampWhenHasDecimal() {
        // Arrange
        val timestamp = "123.456"

        // Act
        val result = normalizeTimestamp(timestamp)

        // Assert
        assertEquals("123", result, "Result should not contain decimals")
    }

    @Tag("normalizeTimestamp")
    @Test
    fun normalizeTimestampShouldReturnTimestampWhenNoDecimal() {
        // Arrange
        val timestamp = "123"

        // Act
        val result = normalizeTimestamp(timestamp)

        // Assert
        assertEquals("123", result, "Result should not contain decimals")
    }

    @Tag("readWaypointsFromCsv")
    @Test
    fun readWaypointsFromCsvShouldReturnListOfWaypoints() {
        // Arrange
        val bufferedReader = StringReader("123.456;45.678;9.012\n123.456;45.678;9.012\n123.456;45.678;9.012").buffered()
        val expectedResult = listOf(
            WayPoint(123, 45.678, 9.012),
            WayPoint(123, 45.678, 9.012),
            WayPoint(123, 45.678, 9.012)
        )

        // Act
        val result = readWaypointsFromCsv(bufferedReader)

        // Assert
        assertEquals(expectedResult, result, "Result does not match expected result")
    }

    @Tag("readWaypointsFromCsv")
    @Test
    fun readWaypointsFromCsvShouldReturnEmptyList() {
        // Arrange
        val bufferedReader = StringReader("").buffered()
        val expectedResult = emptyList<WayPoint>()

        // Act
        val result = readWaypointsFromCsv(bufferedReader)

        // Assert
        assertEquals(expectedResult, result, "Result does not match expected result")
    }

    @Tag("distanceBetweenWayPoints")
    @Test
    fun distanceBetweenWayPointsShouldReturnCorrectDistance() {
        // Arrange
        val point1 = WayPoint(0, 0.0, 0.0)
        val point2 = WayPoint(0, 0.0, 1.0)

        // Act
        val result = distanceBetweenWayPoints(point1, point2)

        // Assert
        assertEquals(111.195, result, 0.001, "Distance should be approximately 111.195 km")
    }

    @Tag("distanceBetweenWayPoints")
    @Test
    fun distanceBetweenWayPointsShouldReturnZeroForSamePoint() {
        // Arrange
        val point1 = WayPoint(0, 0.0, 0.0)

        // Act
        val result = distanceBetweenWayPoints(point1, point1)

        // Assert
        assertEquals(0.0, result, "Distance should be 0 km for the same point")
    }

    @Tag("distanceBetweenWayPoints")
    @Test
    fun distanceBetweenWayPointsShouldHandleNegativeCoordinates() {
        // Arrange
        val point1 = WayPoint(0, -45.0, -45.0)
        val point2 = WayPoint(0, 45.0, 45.0)

        // Act
        val result = distanceBetweenWayPoints(point1, point2)

        // Assert
        assertEquals(13343.0, result, 1.0, "Distance should be approximately 13343 km")
    }

    @Tag("distanceBetweenWayPoints")
    @Test
    fun distanceBetweenWayPointsShouldHandleLargeDistances() {
        // Arrange
        val point1 = WayPoint(0, -90.0, 0.0)
        val point2 = WayPoint(0, 90.0, 0.0)

        // Act
        val result = distanceBetweenWayPoints(point1, point2)

        // Assert
        assertEquals(20015.0, result, 1.0, "Distance should be approximately 20015 km")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun waypointsOutsideGeofenceShouldReturnEmptySequenceWhenAllInside() {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = listOf(
            WayPoint(1, 0.0, 0.1),
            WayPoint(2, 0.1, 0.0),
            WayPoint(3, 0.1, 0.1)
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0)

        // Assert
        assertTrue(result.isEmpty(), "All waypoints should be inside the geofence")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun waypointsOutsideGeofenceShouldReturnAllWaypointsWhenAllOutside() {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = listOf(
            WayPoint(1, 10.0, 10.0),
            WayPoint(2, 20.0, 20.0),
            WayPoint(3, 30.0, 30.0)
        )

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0)

        // Assert
        assertEquals(waypoints, result, "All waypoints should be outside the geofence")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun waypointsOutsideGeofenceShouldReturnCorrectWaypoints() {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = listOf(
            WayPoint(1, 0.0, 0.1),
            WayPoint(2, 10.0, 10.0),
            WayPoint(3, 0.1, 0.1)
        )
        val expectedResult = listOf(WayPoint(2, 10.0, 10.0))

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0)

        // Assert
        assertEquals(expectedResult, result, "The waypoint outside the geofence should be returned")
    }

    @Tag("waypointsOutsideGeofence")
    @Test
    fun waypointsOutsideGeofenceShouldHandleEmptySequence() {
        // Arrange
        val centralWayPoint = WayPoint(0, 0.0, 0.0)
        val waypoints = emptyList<WayPoint>()

        // Act
        val result = waypointsOutsideGeofence(waypoints, centralWayPoint, 100.0)

        // Assert
        assertTrue(result.isEmpty(), "Result should be empty for an empty sequence")
    }
}
