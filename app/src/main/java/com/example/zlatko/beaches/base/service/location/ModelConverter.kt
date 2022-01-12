package com.example.zlatko.beaches.base.service.location

import com.example.zlatko.beaches.base.client.brandenburg.BrandenburgClient
import com.example.zlatko.beaches.base.client.brandenburg.model.Data
import com.example.zlatko.beaches.base.client.brandenburg.model.Placemark
import com.example.zlatko.beaches.base.model.LocationFeature
import com.example.zlatko.beaches.base.repository.location.model.LocationEntity
import com.example.zlatko.beaches.base.service.location.model.Location

class ModelConverter {

    fun convert(placemark: Placemark?, favorites: Set<String>): Location {
        val coordinates = placemark!!.point!!.coordinates.split(",")
        val data = extractData(placemark.extendedData!!.data!!)
        val id = data["bnr"] ?: "N/A"
        return Location(
            id = id,
            name = placemark.name,
            longitude = coordinates[0].toDouble(),
            latitude = coordinates[1].toDouble(),
            temperature = data["temperature"] ?: "N/A",
            lastMeasurementDate = data["lastMeasurementDate"] ?: "N/A",
            visibilityDepth = data["visibilityDepth"] ?: "N/A",
            rating = data["rating"],
            favorite = favorites.contains(id),
            features = extractServices(data),
            imageUrl = "${BrandenburgClient.IMAGE_BASE_URL}${id}",
            detailsUrl = "${BrandenburgClient.DETAILS_BASE_URL}${id}"
        )
    }

    fun convert(location: Location) =
        LocationEntity(
            id = location.id,
            name = location.name,
            longitude = location.longitude,
            latitude = location.latitude,
            temperature = location.temperature,
            lastMeasurementDate = location.lastMeasurementDate,
            visibilityDepth = location.visibilityDepth,
            rating = location.rating,
            favorite = location.favorite,
            features = location.features.joinToString(",")
        )

    fun convert(location: LocationEntity): Location {
        return Location(
            id = location.id,
            name = location.name,
            longitude = location.longitude,
            latitude = location.latitude,
            temperature = location.temperature,
            lastMeasurementDate = location.lastMeasurementDate,
            visibilityDepth = location.visibilityDepth,
            rating = location.rating,
            favorite = location.favorite,
            imageUrl = "${BrandenburgClient.IMAGE_BASE_URL}${location.id}",
            detailsUrl = "${BrandenburgClient.DETAILS_BASE_URL}${location.id}",
            features = location.features
                .split(",")
                .map {
                    LocationFeature.valueOf(it)
                }.toSet()
        )
    }

    private fun extractServices(data: java.util.HashMap<String, String>): Set<LocationFeature> {
        val services = HashSet<LocationFeature>()
        LocationFeature.values().forEach {
            if (data[it.id] == "ja") {
                services.add(it)
            }
        }
        return services
    }

    private fun extractData(data: List<Data?>): HashMap<String, String> {
        val dataMap = HashMap<String, String>()
        data.forEach {
            dataMap[it!!.name] = it.value
        }
        return dataMap
    }
}