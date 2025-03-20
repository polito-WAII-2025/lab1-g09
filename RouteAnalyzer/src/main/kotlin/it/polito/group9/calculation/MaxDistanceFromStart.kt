package it.polito.group9.calculation

import it.polito.group9.model.WayPoint
import it.polito.group9.utils.calculateEarthDistanceHaversine

fun maxDistanceFromStart(wayPoints: List<WayPoint>): Double {
    val startingPoint = wayPoints.first()

    return wayPoints.maxOf { calculateEarthDistanceHaversine(startingPoint, it) }
}