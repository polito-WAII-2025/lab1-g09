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
fun waypointsOutsideGeofence(waypoints: List<WayPoint>, parameters: CustomParameters): List<WayPoint> {
    val centralWayPoint = WayPoint(0, parameters.geofenceCenterLatitude, parameters.geofenceCenterLongitude)
    val earthRadius = parameters.earthRadiusKm

    return waypoints.filter {
        distanceBetweenWayPoints(it, centralWayPoint, earthRadius) > parameters.geofenceRadiusKm
    }
}