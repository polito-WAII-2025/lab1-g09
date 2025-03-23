package it.polito.group9.model

import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader


data class CustomParameters(
    val geofenceCenterLatitude: Double,
    var geofenceCenterLongitude: Double,
    var earthRadiusKm: Double,
    var geofenceRadiusKm: Double,
    var mostFrequentedAreaRadiusKm: Double? = null
)

fun readCustomParameters(reader: BufferedReader): CustomParameters {
    val params = Yaml().loadAs(reader, CustomParameters::class.java)
    require(params.earthRadiusKm != null) { "earthRadiusKm must be provided" }
    require(params.geofenceCenterLatitude != null && params.geofenceCenterLongitude != null) {
        "geofence center must be provided"
    }
    require(params.geofenceRadiusKm != null) { "geofenceRadiusKm must be provided" }
    return params
}
