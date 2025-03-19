package it.polito.group9.model

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.InputStream


class CustomParameters {
    var earthRadiusKm: Double? = null
    var geofenceCenterLatitude: Double? = null
    var geofenceCenterLongitude: Double? = null
    var geofenceRadiusKm: Double? = null
    var mostFrequentedAreaRadiusKm: Double? = null
}

fun readCustomParameters(): CustomParameters {
    val inputStream: InputStream = File("src/main/resources/custom-parameters.yml").inputStream()
    val yaml = Yaml()
    return yaml.loadAs(inputStream, CustomParameters::class.java)
}