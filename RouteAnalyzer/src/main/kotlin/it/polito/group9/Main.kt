package it.polito.group9

import it.polito.group9.lib.maxDistanceFromStart
import it.polito.group9.lib.readWaypointsFromCsv
import it.polito.group9.model.WayPoint
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Usage: RouteAnalyzer <input-file>")
    }

    val path = args[0]
    val inputFile = File(path)
    val wayPoints: List<WayPoint> = readWaypointsFromCsv(inputFile.bufferedReader())

    val maxDistance = maxDistanceFromStart(wayPoints)
    println("The maximum distance from start is: $maxDistance meters.")

}