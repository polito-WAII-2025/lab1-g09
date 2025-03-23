package it.polito.group9.model.result

import it.polito.group9.model.WayPoint
import kotlinx.serialization.Serializable

@Serializable
data class WaypointsOutsideGeofenceResult (
    val centralWayPoint: WayPoint,
    val areaRadiusKm: Double,
    val count: Int,
    val waypoints: List<WayPoint>
)