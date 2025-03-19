package it.polito.group9.lib

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import it.polito.group9.model.WayPoint
import java.io.BufferedReader
import kotlin.math.*

fun readWaypointsFromCsv(bufferedReader: BufferedReader): List<WayPoint> {
    val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
        .withDelimiter(';')
        .withTrim())

    return csvParser.map {
        WayPoint(
            normalizeTimestamp(it[0]).toLong(),
            it[1].toDouble(),
            it[2].toDouble()
        )
    }
}

fun normalizeTimestamp(timestamp: String): String {
    return if (timestamp.contains(".")) {
        timestamp.split(".")[0]
    } else {
        timestamp
    }
}

/**
 * Calculates the distance between two waypoints using the Haversine formula.
 *
 * @param point1 The first waypoint.
 * @param point2 The second waypoint.
 * @param earthRadius The radius of the Earth in kilometers. Default is 6371.0 km.
 * @return The distance between the two waypoints in kilometers.
 */
fun distanceBetweenWayPoints(
    point1: WayPoint, point2: WayPoint,
    earthRadius: Double = 6371.0 // Default Earth radius in kilometers
): Double {
    val lat1Rad = Math.toRadians(point1.latitude)
    val lon1Rad = Math.toRadians(point1.longitude)
    val lat2Rad = Math.toRadians(point2.latitude)
    val lon2Rad = Math.toRadians(point2.longitude)

    val dLat = lat2Rad - lat1Rad
    val dLon = lon2Rad - lon1Rad

    val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c // Distance in the same unit as earthRadius (default: km)
}

/**
 * Filters waypoints that are outside a specified geofence.
 *
 * @param waypoints A sequence of waypoints to be checked.
 * @param centralWayPoint The central waypoint of the geofence.
 * @param radius The radius of the geofence in kilometers.
 * @return A sequence of waypoints that are outside the specified geofence.
 */
fun waypointsOutsideGeofence(waypoints: List<WayPoint>, centralWayPoint: WayPoint, radius: Double): List<WayPoint> {
    return waypoints.filter {
        distanceBetweenWayPoints(it, centralWayPoint) > radius
    }
}

fun maxDistanceFromStart(wayPoints: List<WayPoint>): Double {
    val startingPoint = wayPoints.first()

    return wayPoints.maxOf { distanceBetweenWayPoints(startingPoint, it) }
}
