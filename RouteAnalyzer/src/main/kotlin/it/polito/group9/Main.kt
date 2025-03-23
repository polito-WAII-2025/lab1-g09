package it.polito.group9

import it.polito.group9.calculation.*
import it.polito.group9.model.WayPoint
import it.polito.group9.model.readCustomParameters
import it.polito.group9.utils.readWaypointsFromCsv
import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Usage: RouteAnalyzer <input-file>")
    }

    val path = args[0]
    val inputFile = File(path)
   val streamCustomParams = Thread.currentThread().contextClassLoader
        .getResourceAsStream("custom-parameters.yml")
        ?: throw IllegalArgumentException("custom-parameters.yml not found on classpath")
    val customParameters = readCustomParameters(streamCustomParams.bufferedReader())

    val wayPoints: List<WayPoint> = readWaypointsFromCsv(inputFile.bufferedReader())

    val maxDistance = maxDistanceFromStart(wayPoints)
    println("The maximum distance from start is: $maxDistance meters.")

}