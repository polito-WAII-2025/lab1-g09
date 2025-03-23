package it.polito.group9.model.result

import kotlinx.serialization.Serializable

@Serializable
data class OutputAdvancedResult (
    val totalTravelDistanceResult: TotalTravelDistanceResult
): Result