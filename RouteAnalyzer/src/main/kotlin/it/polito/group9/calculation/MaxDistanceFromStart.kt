package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.utils.distanceBetweenWayPoints

fun maxDistanceFromStart(wayPoints: List<WayPoint>, customParameters: CustomParameters): Double {
    val startingPoint = wayPoints.first()

    return wayPoints.maxOf { distanceBetweenWayPoints(startingPoint, it, customParameters.earthRadiusKm) }
}