package it.polito.group9.utils

import it.polito.group9.model.WayPoint
import org.junit.jupiter.api.Tag
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Tag("normalizeTimestamp")
    @Test
    fun `normalizeTimestamp with decimals` () {
        // Arrange
        val timestamp = "123.456"

        // Act
        val result = normalizeTimestamp(timestamp)

        // Assert
        assertEquals("123", result, "Result should not contain decimals")
    }

    @Tag("normalizeTimestamp")
    @Test
    fun `normalizeTimestamp without decimals` () {
        // Arrange
        val timestamp = "123"

        // Act
        val result = normalizeTimestamp(timestamp)

        // Assert
        assertEquals("123", result, "Result should not contain decimals")
    }

    @Tag("readWaypointsFromCsv")
    @Test
    fun `readWaypointsFromCsv should return list of WayPoints` () {
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
    fun `readWaypointsFromCsv should return empty list`() {
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
    fun `distanceBetweenWayPoints should return correct distance` () {
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
    fun `distanceBetweenWayPoints should return 0 for the same point` () {
        // Arrange
        val point1 = WayPoint(0, 0.0, 0.0)

        // Act
        val result = distanceBetweenWayPoints(point1, point1)

        // Assert
        assertEquals(0.0, result, "Distance should be 0 km for the same point")
    }

    @Tag("distanceBetweenWayPoints")
    @Test
    fun `distanceBetweenWayPoints should handle negative coordinates` () {
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
    fun `distanceBetweenWayPoints should handle large distances` () {
        // Arrange
        val point1 = WayPoint(0, -90.0, 0.0)
        val point2 = WayPoint(0, 90.0, 0.0)

        // Act
        val result = distanceBetweenWayPoints(point1, point2)

        // Assert
        assertEquals(20015.0, result, 1.0, "Distance should be approximately 20015 km")
    }
}
