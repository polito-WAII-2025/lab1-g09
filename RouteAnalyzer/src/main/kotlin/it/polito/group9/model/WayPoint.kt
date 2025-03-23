package it.polito.group9.model

import kotlinx.serialization.Serializable

@Serializable
data class WayPoint(val timestamp: Long, val latitude: Double, val longitude: Double)