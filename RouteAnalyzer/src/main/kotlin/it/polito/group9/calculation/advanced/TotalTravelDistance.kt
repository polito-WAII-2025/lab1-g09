package it.polito.group9.calculation.advanced

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.TotalTravelDistanceResult
import it.polito.group9.utils.distanceBetweenWayPoints


/**
 * Calculates the total travel distance (km) between a list of waypoints.
 *
 * @param waypoints The list of waypoints to calculate the total travel distance for.
 * @param customParameters Custom parameters used for distance calculation, including the Earth's radius.
 * @return A TotalTravelDistanceResult containing the total distance and the number of waypoints.
 */
fun totalTravelDistance(waypoints: List<WayPoint>, customParameters: CustomParameters): TotalTravelDistanceResult {
    val earthRadius = customParameters.earthRadiusKm
    val totalDistance = waypoints.zipWithNext().sumOf {
        (wp1, wp2) -> distanceBetweenWayPoints(wp1, wp2, earthRadius)
    }

    return TotalTravelDistanceResult(totalDistance, waypoints.size)
}