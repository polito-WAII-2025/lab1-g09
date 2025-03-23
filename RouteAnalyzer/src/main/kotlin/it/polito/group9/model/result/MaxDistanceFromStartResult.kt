package it.polito.group9.model.result

import it.polito.group9.model.WayPoint
import kotlinx.serialization.Serializable

@Serializable
data class MaxDistanceFromStartResult (
    val wayPoint: WayPoint,
    val distanceKm: Double,
)