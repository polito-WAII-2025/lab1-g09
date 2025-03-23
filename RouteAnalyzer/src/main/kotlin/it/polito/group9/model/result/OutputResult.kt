package it.polito.group9.model.result

import kotlinx.serialization.Serializable

@Serializable
sealed interface Result

@Serializable
data class OutputResult (
    val maxDistanceFromStart: MaxDistanceFromStartResult,
    val mostFrequentedArea: MostFrequentedAreaResult,
    val waypointsOutsideGeofence: WaypointsOutsideGeofenceResult
): Result