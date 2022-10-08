package com.example.zlatko.beaches.data.berlin

import com.example.zlatko.beaches.data.BathingAreas
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BerlinBathingAreasRepository @Inject constructor(
    private val berlinBathingAreasNetworkDataSource: BerlinBathingAreasNetworkDataSource,
    private val berlinDataNetworkDataSource: BerlinDataNetworkDataSource
) {
    companion object {
        const val ID_PREFIX = "BERLIN"
    }

    private var bathingAreas: List<BathingAreas> = emptyList()

    suspend fun getBathingAreas(): List<BathingAreas> {
        if (bathingAreas.isNotEmpty()) {
            return bathingAreas
        }
        val bathingAreasInCSV =
            berlinBathingAreasNetworkDataSource.getBathingAreasInCSV()!!.string()
        val dataInCSV = berlinDataNetworkDataSource.getDataInCSV()!!.string()

        val data = csvReader().readAllWithHeader(dataInCSV)

        bathingAreas = csvReader().readAllWithHeader(bathingAreasInCSV).map { bathingArea ->
            val id = bathingArea["id"]!!
            BathingAreas(
                id = "$ID_PREFIX|$id",
                name = bathingArea["name"]!!,
                longitude = bathingArea["lat"]!!.toDouble(),
                latitude = bathingArea["lng"]!!.toDouble(),
                temperature = bathingArea["temp_txt"]!!,
                lastMeasurementDate = bathingArea["m_date"]!!,
                imageUrl = bathingArea["image"]?.replaceFirst("http", "https"),
            )
        }
        return bathingAreas
    }
}
