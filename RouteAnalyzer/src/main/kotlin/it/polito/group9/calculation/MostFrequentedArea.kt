package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.MostFrequentedAreaResult
import it.polito.group9.utils.distanceBetweenWayPoints


// Computes the farthest distance from the starting waypoint.
fun maxDistanceFromStart(waypoints: List<WayPoint>, earthRadius: Double): Pair<WayPoint, Double> {
    val start = waypoints.firstOrNull() ?: throw IllegalArgumentException("No waypoints provided")
    val maxDistance = waypoints.drop(1)
        .map { distanceBetweenWayPoints(start, it, earthRadius) }
        .maxOrNull() ?: 0.0
    return start to maxDistance
}

fun computeDefaultMostFrequentedAreaRadius(farthestDistance: Double): Double {
    return if (farthestDistance < 1.0) 0.1 else farthestDistance * 0.1
}

fun mostFrequentedArea(
    waypoints: List<WayPoint>,
    parameters: CustomParameters
): MostFrequentedAreaResult {
    val earthRadius = parameters.earthRadiusKm

    val (_, farthestDistance) = maxDistanceFromStart(waypoints, earthRadius)

    val areaRadiusKm = parameters.mostFrequentedAreaRadiusKm ?: computeDefaultMostFrequentedAreaRadius(farthestDistance)

    val clusters = waypoints.fold(emptyList<List<WayPoint>>()) { clusters, wp ->
        val clusterIndex = clusters.indexOfFirst { cluster ->
            distanceBetweenWayPoints(cluster.first(), wp, parameters.earthRadiusKm) <= areaRadiusKm
        }
        if (clusterIndex >= 0) {
            clusters.mapIndexed { index, cluster ->
                if (index == clusterIndex) cluster + wp else cluster
            }
        } else {
            clusters + listOf(listOf(wp))
        }
    }
    val mostFrequentedCluster = clusters.maxByOrNull { it.size } ?: emptyList()
    val centralWaypoint = mostFrequentedCluster.firstOrNull() ?: WayPoint(0, 0.0, 0.0)
    return MostFrequentedAreaResult(centralWaypoint, areaRadiusKm, mostFrequentedCluster.size)
}
