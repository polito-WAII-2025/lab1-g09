package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.AverageWaypointsDistanceResult


/**
 * Calculates the average distance (km) between waypoints.
 *
 * @param waypoints The list of waypoints to calculate the average distance for.
 * @param customParameters Custom parameters used for distance calculation, including the Earth's radius.
 * @return An AverageWaypointsDistanceResult containing the average distance and the number of waypoints.
 */
fun averageWaypointsDistance(waypoints: List<WayPoint>, customParameters: CustomParameters):
        AverageWaypointsDistanceResult {
    if (waypoints.size < 2) {
        return AverageWaypointsDistanceResult(0.0, waypoints.size)
    }

    val totalDistance = totalTravelDistance(waypoints, customParameters)
    return AverageWaypointsDistanceResult(totalDistance.totalDistance / waypoints.size, waypoints.size)
}