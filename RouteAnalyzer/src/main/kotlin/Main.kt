package org.example

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.example.model.WayPoint
import java.io.BufferedReader
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Usage: RouteAnalyzer <input-file>")
    }

    val path = args[0]
    val inputFile = File(path)
    val wayPoints = readWaypointsFromCsv(inputFile.bufferedReader())
}

fun readWaypointsFromCsv(bufferedReader: BufferedReader): List<WayPoint> {
    val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
        .withDelimiter(';')
        .withTrim())

    return csvParser.map {
        WayPoint(
            it[0].split(".")[0].toLong(),
            it[1].toDouble(),
            it[2].toDouble()
        )
    }
}