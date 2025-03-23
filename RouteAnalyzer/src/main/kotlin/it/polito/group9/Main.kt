package it.polito.group9

import it.polito.group9.calculation.maxDistanceFromStart
import it.polito.group9.calculation.mostFrequentedArea
import it.polito.group9.calculation.waypointsOutsideGeofence
import it.polito.group9.model.readCustomParameters
import it.polito.group9.model.result.OutputResult
import it.polito.group9.utils.readWaypointsFromCsv
import it.polito.group9.utils.writeResultToFile
import java.io.File

fun main(args: Array<String>) {
    if (args.size < 2) {
        throw IllegalArgumentException("Usage: RouteAnalyzer <input-file> <output-file>")
    }

    // Parse custom parameters
    val customParamsFile = File("/app/resources/custom-parameters.yml")
    val customParameters = customParamsFile.bufferedReader().use { reader ->
        readCustomParameters(reader)
    }

    // Parse waypoints
    val inputPath = args[0]
    val inputFile = File(inputPath)
    val wayPoints = inputFile.bufferedReader().use { reader ->
        readWaypointsFromCsv(reader)
    }

    // Calculate results
    val result = OutputResult(
        maxDistanceFromStart(wayPoints, customParameters),
        mostFrequentedArea(wayPoints, customParameters),
        waypointsOutsideGeofence(wayPoints, customParameters)
    )

    // Write result to file
    val outputPath = args[1]
    val outputFile = File(outputPath)
    outputFile.bufferedWriter().use { out ->
        writeResultToFile(out, result)
    }
}