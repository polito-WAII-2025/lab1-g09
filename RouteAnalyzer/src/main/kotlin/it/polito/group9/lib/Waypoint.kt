package it.polito.group9.lib

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import it.polito.group9.model.WayPoint
import java.io.BufferedReader

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