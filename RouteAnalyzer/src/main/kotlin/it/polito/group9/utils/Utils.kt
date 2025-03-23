package it.polito.group9.utils

import it.polito.group9.model.WayPoint
import it.polito.group9.model.result.Result
import kotlinx.serialization.json.Json
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.BufferedWriter
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

fun writeResultToFile(bufferedWriter: BufferedWriter, result: Result) {
    bufferedWriter.write(Json.encodeToString(result))
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
    point1: WayPoint, point2: WayPoint, earthRadius: Double
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
