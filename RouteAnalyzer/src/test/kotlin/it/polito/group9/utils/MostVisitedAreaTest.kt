
import it.polito.group9.calculations.computeDefaultMostFrequentedAreaRadius
import it.polito.group9.calculations.haversineDistance
import it.polito.group9.calculations.maxDistanceFromStart
import it.polito.group9.calculations.mostFrequentedArea
import it.polito.group9.model.WayPoint
import it.polito.group9.model.CustomParameters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Tolerance for comparing doubles
private const val DELTA = 0.02

class CalculationsTest {

    // Test haversineDistance: when both waypoints are identical, the distance must be 0.
    @Test
    fun testHaversineDistanceZero() {
        val wp = WayPoint(123, 45.678, 9.012)
        val distance = haversineDistance(wp, wp)
        assertEquals(0.0, distance, DELTA, "Distance between the same points should be zero")
    }

    // Test haversineDistance: using a slight difference in latitude.
    @Test
    fun testHaversineDistanceSlightLatitudeDifference() {
        // Using the sample value and a slight change in latitude.
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.679, 9.012) // approx 0.111 km apart in latitude
        val distance = haversineDistance(wp1, wp2)
        // 1 degree latitude ~111 km; hence 0.001° ~0.111 km.
        val expected = 0.111
        assertEquals(expected, distance, 0.02, "Haversine distance for a 0.001° latitude difference should be around 0.111 km")
    }

    // Test maxDistanceFromStart: with an empty list, it should throw an exception.
    @Test
    fun testMaxDistanceFromStartEmptyList() {
        assertFailsWith<IllegalArgumentException>("No waypoints provided should throw IllegalArgumentException") {
            maxDistanceFromStart(emptyList())
        }
    }

    // Test maxDistanceFromStart: for a single waypoint, the maximum distance should be 0.
    @Test
    fun testMaxDistanceFromStartSingleElement() {
        val wp = WayPoint(123, 45.678, 9.012)
        val (start, maxDist) = maxDistanceFromStart(listOf(wp))
        assertEquals(wp, start, "Start waypoint should be the only element")
        assertEquals(0.0, maxDist, DELTA, "Max distance should be zero when only one waypoint is provided")
    }

    // Test maxDistanceFromStart: with multiple waypoints.
    @Test
    fun testMaxDistanceFromStartMultiple() {
        // Define waypoints using sample values with small variations.
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.679, 9.012) // about 0.111 km north of wp1
        val wp3 = WayPoint(123, 45.680, 9.012) // about 0.222 km north of wp1
        val (start, maxDist) = maxDistanceFromStart(listOf(wp1, wp2, wp3))
        val expectedDistance = haversineDistance(wp1, wp3)
        assertEquals(wp1, start, "Start waypoint should be the first element in the list")
        assertEquals(expectedDistance, maxDist, DELTA, "Max distance should equal the distance from the start to the farthest waypoint")
    }

    // Test computeDefaultMostFrequentedAreaRadius when farthestDistance is less than 1 km.
    @Test
    fun testComputeDefaultMostFrequentedAreaRadiusWhenLessThanOne() {
        val farthestDistance = 0.5
        val radius = computeDefaultMostFrequentedAreaRadius(farthestDistance)
        assertEquals(0.1, radius, DELTA, "When farthest distance is less than 1, default radius should be 0.1")
    }

    // Test computeDefaultMostFrequentedAreaRadius when farthestDistance is 1 km or more.
    @Test
    fun testComputeDefaultMostFrequentedAreaRadiusWhenGreaterThanOrEqualToOne() {
        val farthestDistance = 10.0
        val radius = computeDefaultMostFrequentedAreaRadius(farthestDistance)
        val expected = 10.0 * 0.1
        assertEquals(expected, radius, DELTA, "When farthest distance is >= 1, default radius should be 10% of farthest distance")
    }

    // Test mostFrequentedArea: when the list is empty, it should throw an exception.
    @Test
    fun testMostFrequentedAreaEmptyList() {
        val parameters = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.5
        }
        assertFailsWith<IllegalArgumentException>("No waypoints provided should throw an exception") {
            mostFrequentedArea(emptyList(), parameters)
        }
    }

    // Test mostFrequentedArea: with a single waypoint.
    @Test
    fun testMostFrequentedAreaSinglePoint() {
        val wp = WayPoint(123, 45.678, 9.012)
        val parameters = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.5
        }

        val (central, count) = mostFrequentedArea(listOf(wp), parameters)
        assertEquals(wp, central, "With a single waypoint, it should be the central waypoint")
        assertEquals(1, count, "With a single waypoint, the cluster count should be 1")
    }

    // Test mostFrequentedArea: clustering using the default radius (computed from farthest distance).
    @Test
    fun testMostFrequentedAreaClusteringDefaultRadius() {
        // Create several waypoints close to each other using sample values with small variations.
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.6785, 9.0125)
        val wp3 = WayPoint(123, 45.6787, 9.0127)
        // With these small differences the farthest distance is below 1 km, so default radius = 0.1 km.
        val parameters = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.5
        }
        val (central, count) = mostFrequentedArea(listOf(wp1, wp2, wp3), parameters)
        assertEquals(3, count, "All waypoints should be clustered together with the default radius")
        assertEquals(wp1, central, "The central waypoint should be the first waypoint in the cluster")
    }

    // Test mostFrequentedArea: with a provided area radius forcing specific clustering.
    @Test
    fun testMostFrequentedAreaWithProvidedRadius() {
        // Define two clusters:
        // Cluster 1: Two points close together.
        val wp1 = WayPoint(123, 45.678, 9.012)
        val wp2 = WayPoint(123, 45.6784, 9.0124) // close to wp1
        // Cluster 2: One point farther away.
        val wp3 = WayPoint(123, 45.680, 9.016)
        // Providing a radius (0.2 km) that clusters wp1 and wp2 together.
        val parameters = CustomParameters().apply {
            earthRadiusKm = 6371.0
            mostFrequentedAreaRadiusKm = 0.2
        }
        val (central, count) = mostFrequentedArea(listOf(wp1, wp2, wp3), parameters)
        assertEquals(2, count, "The cluster with wp1 and wp2 should be identified as the most frequented area")
        assertEquals(wp1, central, "The central waypoint should be the first point of the cluster")
    }
}
