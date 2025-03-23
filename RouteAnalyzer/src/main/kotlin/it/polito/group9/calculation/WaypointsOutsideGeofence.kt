package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.utils.distanceBetweenWayPoints

/**
 * Filters waypoints that are outside a specified geofence.
 *
 * @param waypoints A sequence of waypoints to be checked.
 * @param centralWayPoint The central waypoint of the geofence.
 * @param radius The radius of the geofence in kilometers.
 * @return A sequence of waypoints that are outside the specified geofence.
 */
fun waypointsOutsideGeofence(waypoints: List<WayPoint>, centralWayPoint: WayPoint, radius: Double, parameters: CustomParameters): List<WayPoint> {
    return waypoints.filter {
        val earthRadius = parameters.earthRadiusKm ?: 6371.0
        distanceBetweenWayPoints(it, centralWayPoint, earthRadius) > radius
    }
}