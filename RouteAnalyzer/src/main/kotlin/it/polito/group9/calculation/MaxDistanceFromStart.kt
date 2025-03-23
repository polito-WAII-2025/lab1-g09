package it.polito.group9.calculation

import it.polito.group9.model.WayPoint
import it.polito.group9.utils.distanceBetweenWayPoints

fun maxDistanceFromStart(wayPoints: List<WayPoint>): Double {
    val startingPoint = wayPoints.first()

    return wayPoints.maxOf { distanceBetweenWayPoints(startingPoint, it) }
}