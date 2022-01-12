package com.example.zlatko.beaches.base.service.location

import com.example.zlatko.beaches.base.client.brandenburg.BrandenburgClient
import com.example.zlatko.beaches.base.model.LocationFeature
import com.example.zlatko.beaches.base.repository.location.LocationDao
import com.example.zlatko.beaches.base.service.location.model.Location

class LocationService(
    private val locationDao: LocationDao,
    private val brandenburgClient: BrandenburgClient,
    private val modelConverter: ModelConverter
) {

    suspend fun getLocations(
        withFeatures: Set<LocationFeature> = emptySet(),
        onlyIfFavorite: Boolean = false
    ): List<Location> {
        val filterWithFeatures: (t: Location) -> Boolean = { location ->
            location.features.containsAll(withFeatures)
        }

        val filterIfFavorite: (t: Location) -> Boolean = { location ->
            !onlyIfFavorite or location.favorite
        }
        var locations = getLocationsFromDB()

        if (locations.isEmpty()) {
            locations = getLocationsFromClient()
            locationDao.insertAll(locations.map { modelConverter.convert(it) })
        }

        return locations
            .filter(filterWithFeatures)
            .filter(filterIfFavorite)
    }

    suspend fun update(location: Location): Int {
        val locations = modelConverter.convert(location)
        return locationDao.update(locations)
    }

    private suspend fun getLocationsFromDB() = locationDao.getAll()
        .map { modelConverter.convert(it) }

    private suspend fun getLocationsFromClient(): List<Location> {
        val favorites = locationDao.getFavorites().map { it.id }.toSet()

        val locationsInKml = brandenburgClient.getLocationsInKml()
        return locationsInKml!!.document!!.placemark!!.map {
            modelConverter.convert(it, favorites)
        }
    }

    suspend fun getLocation(id: String): Location {
        return modelConverter.convert(locationDao.findById(id))
    }

}
