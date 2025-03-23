package it.polito.group9.model

import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader
import java.io.File
import java.io.InputStream


class CustomParameters {
    var earthRadiusKm: Double? = null
    var geofenceCenterLatitude: Double? = null
    var geofenceCenterLongitude: Double? = null
    var geofenceRadiusKm: Double? = null
    var mostFrequentedAreaRadiusKm: Double? = null
}

fun readCustomParameters(reader: BufferedReader): CustomParameters {
    val params = Yaml().loadAs(reader, CustomParameters::class.java)
    require(params.earthRadiusKm != null) { "earthRadiusKm must be provided" }
    require(params.geofenceCenterLatitude != null && params.geofenceCenterLongitude != null) {
        "geofence center must be provided"
    }
    require(params.geofenceRadiusKm != null) { "geofenceRadiusKm must be provided" }
    return params
}
