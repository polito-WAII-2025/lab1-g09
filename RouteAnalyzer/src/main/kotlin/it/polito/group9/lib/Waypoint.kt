package it.polito.group9.lib

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import it.polito.group9.model.WayPoint
import java.io.BufferedReader
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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
