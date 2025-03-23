package it.polito.group9.model.result

import kotlinx.serialization.Serializable

@Serializable
data class AverageSpeedResult (
    val averageSpeed: Double,
    val totalDistance: Double,
    val totalTime: Double,
    val entriesCount: Int
)