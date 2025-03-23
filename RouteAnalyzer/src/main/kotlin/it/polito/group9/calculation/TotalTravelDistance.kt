package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.TotalTravelDistanceResult
import it.polito.group9.utils.distanceBetweenWayPoints

fun totalTravelDistance(waypoints: List<WayPoint>, customParameters: CustomParameters): TotalTravelDistanceResult {
    val earthRadius = customParameters.earthRadiusKm
    val totalDistance = waypoints.zipWithNext().sumOf {
        (wp1, wp2) -> distanceBetweenWayPoints(wp1, wp2, earthRadius)
    }

    return TotalTravelDistanceResult(totalDistance, waypoints.size)
}