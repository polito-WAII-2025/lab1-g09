package it.polito.group9.model

import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader


class CustomParameters {
    var earthRadiusKm: Double? = null
    var geofenceCenterLatitude: Double? = null
    var geofenceCenterLongitude: Double? = null
    var geofenceRadiusKm: Double? = null
    var mostFrequentedAreaRadiusKm: Double? = null
}

fun readCustomParameters(reader: BufferedReader): CustomParameters {
    val yaml = Yaml()
    return yaml.loadAs(reader, CustomParameters::class.java)
}