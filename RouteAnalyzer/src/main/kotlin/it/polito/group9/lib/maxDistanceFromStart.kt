package it.polito.group9.lib

import it.polito.group9.model.WayPoint
import kotlin.math.*

fun calculateEarthDistanceHaversine(p1: WayPoint, p2: WayPoint): Double {
    val earthRadius = 6371.0 // Earth's radius in kilometers

    //non tipizzato perchè tutti double

    val lat1 = Math.toRadians(p1.latitude);
    val lon1 = Math.toRadians(p1.longitude);
    val lat2 = Math.toRadians(p2.latitude);
    val lon2 = Math.toRadians(p2.longitude);

    val dLat = lat2 - lat1;
    val dLon = lon2 - lon1;

    val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2);
    val c = 2 * atan2(sqrt(a), sqrt(1 - a));

    return earthRadius * c;
}

fun maxDistanceFromStart(wayPoints: List<WayPoint>): Double {
    val startingPoint = wayPoints.first()

    return wayPoints.maxOf { calculateEarthDistanceHaversine(startingPoint, it) }
}

