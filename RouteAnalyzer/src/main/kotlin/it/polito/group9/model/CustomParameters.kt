package it.polito.group9.model

import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader


data class CustomParameters(
    val geofenceCenterLatitude: Double,
    val geofenceCenterLongitude: Double,
    val earthRadiusKm: Double,
    val geofenceRadiusKm: Double,
    val mostFrequentedAreaRadiusKm: Double? = null
)

fun readCustomParameters(reader: BufferedReader): CustomParameters {
    val raw = Yaml().load(reader) as Map<String, Any?>

    val geofenceCenterLatitude = raw["geofenceCenterLatitude"] as? Double
        ?: throw IllegalArgumentException("geofenceCenterLatitude must be provided")
    val geofenceCenterLongitude = raw["geofenceCenterLongitude"] as? Double
        ?: throw IllegalArgumentException("geofenceCenterLongitude must be provided")
    val earthRadiusKm = raw["earthRadiusKm"] as? Double
        ?: throw IllegalArgumentException("earthRadiusKm must be provided")
    val geofenceRadiusKm = raw["geofenceRadiusKm"] as? Double
        ?: throw IllegalArgumentException("geofenceRadiusKm must be provided")

    val mostFrequentedAreaRadiusKm = raw["mostFrequentedAreaRadiusKm"] as? Double

    return CustomParameters(
        geofenceCenterLatitude = geofenceCenterLatitude,
        geofenceCenterLongitude = geofenceCenterLongitude,
        earthRadiusKm = earthRadiusKm,
        geofenceRadiusKm = geofenceRadiusKm,
        mostFrequentedAreaRadiusKm = mostFrequentedAreaRadiusKm
    )
}
