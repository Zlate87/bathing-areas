package com.example.zlatko.beaches.data.brandenburg

import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.data.FavoritesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrandenburgBathingAreasRepository @Inject constructor(
    private val networkDataSource: BrandenburgBathingAreasNetworkDataSource,
) {
    companion object {
        const val ID_PREFIX = "BRANDENBURG"
    }

    private var bathingAreas: List<BathingAreas> = emptyList()

    suspend fun getBathingAreas(): List<BathingAreas> {
        if (bathingAreas.isNotEmpty()) {
            return bathingAreas
        }
        val locationsInKml = networkDataSource.getBathingAreasInKml()
        bathingAreas = locationsInKml!!.document!!.placemark!!.map {
            convert(it)
        }
        return bathingAreas
    }

    private fun convert(placeMark: Placemark?): BathingAreas {
        val coordinates = placeMark!!.point!!.coordinates.split(",")
        val data = extractData(placeMark.extendedData!!.data!!)
        val id = data["bnr"] ?: "N/A"
        return BathingAreas(
            id = "$ID_PREFIX|$id",
            name = placeMark.name,
            longitude = coordinates[0].toDouble(),
            latitude = coordinates[1].toDouble(),
            temperature = data["temperature"] ?: "N/A",
            lastMeasurementDate = data["lastMeasurementDate"] ?: "N/A",
            visibilityDepth = data["visibilityDepth"] ?: "N/A",
            rating = data["rating"],
            features = extractServices(data),
            imageUrl = "${BrandenburgBathingAreasNetworkDataSource.IMAGE_BASE_URL}${id}.jpg",
            detailsUrl = "${BrandenburgBathingAreasNetworkDataSource.DETAILS_BASE_URL}${id}"
        )
    }


    private fun extractServices(data: HashMap<String, String>): Set<BathingAreas.Feature> {
        val services = HashSet<BathingAreas.Feature>()
        BathingAreas.Feature.values().forEach {
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