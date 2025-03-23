package it.polito.group9.calculation

import it.polito.group9.model.CustomParameters
import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.MaxDistanceFromStartResult
import it.polito.group9.utils.distanceBetweenWayPoints

fun maxDistanceFromStart(wayPoints: List<WayPoint>, customParameters: CustomParameters): MaxDistanceFromStartResult {
    val startingPoint = wayPoints.first()

    val maxPair = wayPoints.map {
        Pair(it, distanceBetweenWayPoints(startingPoint, it, customParameters.earthRadiusKm))
    }.maxBy {
        it.second
    }

    return MaxDistanceFromStartResult(maxPair.first, maxPair.second)
}