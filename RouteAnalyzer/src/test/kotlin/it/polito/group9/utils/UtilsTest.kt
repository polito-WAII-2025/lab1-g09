package it.polito.group9.utils

import it.polito.group9.model.WayPoint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import java.io.StringReader
import kotlin.math.round
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
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

    @Tag("calculateEarthDistance")
    @Test
    fun `test calculateEarthDistance between known points`() {
        val p1 = WayPoint(0, 41.9028, 12.4964) // Roma
        val p2 = WayPoint(0, 48.8566, 2.3522)  // Parigi

        val distance = calculateEarthDistanceHaversine(p1, p2)

        // La distanza attesa tra Roma e Parigi è circa 1100 km
        Assertions.assertEquals(1100.0, round(distance), 5.0)
    }

    @Tag("calculateEarthDistance")
    @Test
    fun `test calculateEarthDistance with same point`() {
        val p = WayPoint(0, 45.0, 9.0)

        val distance = calculateEarthDistanceHaversine(p, p)

        // Se i due punti sono uguali, la distanza deve essere 0.0
        Assertions.assertEquals(0.0, distance, 0.0001)
    }

    @Tag("calculateEarthDistance")
    @Test
    fun `test calculateEarthDistance along meridian`() {
        val p1 = WayPoint(0, 0.0, 0.0)    // Equatore, longitudine 0
        val p2 = WayPoint(0, 10.0, 0.0)   // 10° Nord, stessa longitudine

        val distance = calculateEarthDistanceHaversine(p1, p2)

        // 10 gradi di latitudine ~ 1110 km
        Assertions.assertEquals(1110.0, round(distance), 5.0)
    }
}