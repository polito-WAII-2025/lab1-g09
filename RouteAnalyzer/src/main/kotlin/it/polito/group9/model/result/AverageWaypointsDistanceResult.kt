package it.polito.group9.model.result

import kotlinx.serialization.Serializable

@Serializable
data class AverageWaypointsDistanceResult(
    val averageDistance: Double,
    val entriesCount: Int
)
