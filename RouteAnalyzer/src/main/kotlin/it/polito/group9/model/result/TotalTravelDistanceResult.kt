package it.polito.group9.model.result

import kotlinx.serialization.Serializable

@Serializable
data class TotalTravelDistanceResult (
    val totalDistance: Double,
    val entriesCount: Int
)