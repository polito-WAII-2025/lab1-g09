package it.polito.group9.lib

import it.polito.group9.model.WayPoint
import org.junit.jupiter.api.Tag
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertEquals

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
}