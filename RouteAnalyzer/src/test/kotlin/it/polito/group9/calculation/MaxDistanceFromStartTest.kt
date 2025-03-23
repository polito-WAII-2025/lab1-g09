package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import kotlin.math.round
import kotlin.test.Test

class MaxDistanceFromStartTest {
    val parameters = CustomParameters(
        41.9028,
        12.4964,
        6371.0,
        0.1,
        0.1
    )

    @Tag("maxDistanceFromStart")
    @Test
    fun `test maxDistanceFromStart with multiple points`() {
        val wayPoints = listOf(
            WayPoint(0, 41.9028, 12.4964), // Roma (punto di partenza)
            WayPoint(0, 48.8566, 2.3522),  // Parigi
            WayPoint(0, 40.7128, -74.0060) // New York
        )

        val maxDistance = maxDistanceFromStart(wayPoints, parameters)

        // New York è il punto più distante da Roma (~6889 km)
        Assertions.assertEquals(6889.0, round(maxDistance), 5.0)
    }

    @Tag("maxDistanceFromStart")
    @Test
    fun `test maxDistanceFromStart with single point`() {
        val wayPoints = listOf(WayPoint(0, 41.9028, 12.4964)) // Solo Roma

        val maxDistance = maxDistanceFromStart(wayPoints, parameters)

        // Se c'è un solo WayPoint, la distanza massima è 0.0
        Assertions.assertEquals(0.0, maxDistance, 0.0001)
    }
}