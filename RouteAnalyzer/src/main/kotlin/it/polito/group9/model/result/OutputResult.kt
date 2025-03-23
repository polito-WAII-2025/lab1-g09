package it.polito.group9.model.result

import kotlinx.serialization.Serializable

@Serializable
data class OutputResult (
    val maxDistanceFromStart: MaxDistanceFromStartResult,
    val mostFrequentedArea: MostFrequentedAreaResult,
    val waypointsOutsideGeofence: WaypointsOutsideGeofenceResult
)