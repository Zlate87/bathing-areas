package com.example.zlatko.beaches.base.service.location.model

import com.example.zlatko.beaches.base.model.LocationFeature

data class Location(
    val id: String,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val temperature: String,
    val lastMeasurementDate: String,
    val visibilityDepth: String,
    val imageUrl: String,
    val detailsUrl: String,
    val rating: String?,
    val features: Set<LocationFeature>,
    var favorite: Boolean
)

