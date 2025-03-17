package it.polito.group9.calculations

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import kotlin.math.*

fun haversineDistance(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double,
    earthRadius: Double = 6371.0
): Double {
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2)
    return 2 * earthRadius * atan2(sqrt(a), sqrt(1 - a))
}

// Computes the farthest distance from the starting waypoint.
// Returns a pair: (starting waypoint, farthest distance in km)
fun maxDistanceFromStart(waypoints: List<WayPoint>, earthRadius: Double = 6371.0): Pair<WayPoint, Double> {
    if (waypoints.isEmpty()) throw IllegalArgumentException("No waypoints provided")
    val start = waypoints.first()
    var maxDistance = 0.0
    for (wp in waypoints) {
        val distance = haversineDistance(
            start.latitude, start.longitude,
            wp.latitude, wp.longitude,
            earthRadius
        )
        if (distance > maxDistance) {
            maxDistance = distance
        }
    }
    return Pair(start, maxDistance)
}

// Returns 0.1 km if farthestDistance < 1 km; otherwise, returns 10% of farthestDistance.
fun computeDefaultMostFrequentedAreaRadius(farthestDistance: Double): Double {
    return if (farthestDistance < 1.0) 0.1 else farthestDistance * 0.1
}

fun mostFrequentedArea(waypoints: List<WayPoint>, parameters: CustomParameters): Pair<WayPoint, Int> {
    // Use provided earthRadiusKm or fallback to 6371.0 km if null.
    val earthRadius = parameters.earthRadiusKm ?: 6371.0

    val (_, farthestDistance) = maxDistanceFromStart(waypoints, earthRadius)

    val areaRadiusKm = parameters.mostFrequentedAreaRadiusKm ?: computeDefaultMostFrequentedAreaRadius(farthestDistance)
    println("Using area radius: $areaRadiusKm km (Farthest distance: $farthestDistance km)")

    val clusters = mutableListOf<MutableList<WayPoint>>()
    for (wp in waypoints) {
        var addedToCluster = false
        for (cluster in clusters) {
            val centralPoint = cluster[0]
            val distance = haversineDistance(
                centralPoint.latitude, centralPoint.longitude,
                wp.latitude, wp.longitude,
                earthRadius
            )
            if (distance <= areaRadiusKm) {
                cluster.add(wp)
                addedToCluster = true
                break
            }
        }
        if (!addedToCluster) {
            clusters.add(mutableListOf(wp))
        }
    }

    val mostFrequentedCluster = clusters.maxByOrNull { it.size } ?: emptyList()
    val centralWaypoint = mostFrequentedCluster.firstOrNull() ?: WayPoint(0, 0.0, 0.0)
    return Pair(centralWaypoint, mostFrequentedCluster.size)
}
