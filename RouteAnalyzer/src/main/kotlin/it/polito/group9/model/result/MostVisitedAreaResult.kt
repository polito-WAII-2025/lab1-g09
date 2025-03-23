package it.polito.group9.model.result

import it.polito.group9.model.WayPoint
import kotlinx.serialization.Serializable

@Serializable
data class MostVisitedAreaResult(
    val centralWayPoint: WayPoint,
    val areaRadiusKm: Double,
    val entriesCount: Int,
)