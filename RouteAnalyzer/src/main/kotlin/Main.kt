package org.example

import org.example.lib.readWaypointsFromCsv
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Usage: RouteAnalyzer <input-file>")
    }

    val path = args[0]
    val inputFile = File(path)
    val wayPoints = readWaypointsFromCsv(inputFile.bufferedReader())
}