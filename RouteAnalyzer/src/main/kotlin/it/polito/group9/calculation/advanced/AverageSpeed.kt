package it.polito.group9.calculation.advanced

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.AverageSpeedResult

/**
 * Calculates the average speed (kph) between a list of waypoints.
 *
 * @param wayPoints The list of waypoints to calculate the average speed for.
 * @param customParameters Custom parameters used for distance calculation.
 * @return An AverageSpeedResult containing the average speed, total distance, total time, and waypoint count.
 */
fun averageSpeed(wayPoints: List<WayPoint>, customParameters: CustomParameters): AverageSpeedResult {
    if (wayPoints.size < 2) {
        return AverageSpeedResult(0.0, 0.0, 0.0, wayPoints.size)
    }
    val totalDistance = totalTravelDistance(wayPoints, customParameters)
    val totalTime = (wayPoints.last().timestamp - wayPoints.first().timestamp).toDouble() / (1000*3600)
    val averageSpeed = totalDistance.totalDistance / totalTime

    return AverageSpeedResult(averageSpeed, totalDistance.totalDistance, totalTime, wayPoints.size)
}