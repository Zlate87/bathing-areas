package com.example.zlatko.beaches.features.map.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zlatko.beaches.base.model.LocationFeature
import com.example.zlatko.beaches.base.service.location.LocationService
import com.example.zlatko.beaches.base.service.location.model.Location
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val locationService: LocationService
) : ViewModel() {

    companion object {
        private val BERLIN_COORDINATES = LatLng(52.520008, 13.404954)
        private const val ACTIVE_LOCATION_STATE_KEY = "ACTIVE_LOCATION_STATE_KEY"
    }

    var mapCenter = BERLIN_COORDINATES
    var activeLocation: Location? = null
    val locations = mutableStateOf(emptyList<Location>())
    val showOnlyFavorites = mutableStateOf(false)
    val filter = mutableStateOf(hashSetOf<LocationFeature>())

    init {
        loadLocations()
    }

    fun loadLocations() {
        val onlyIfFavorite = showOnlyFavorites.value
        val withFeatures = filter.value
        viewModelScope.launch(Dispatchers.IO) {
            val result = locationService.getLocations(withFeatures, onlyIfFavorite)
            locations.value = result

            if (savedStateHandle.get<String>(ACTIVE_LOCATION_STATE_KEY) != activeLocation?.id) {
                onLocationClicked(ACTIVE_LOCATION_STATE_KEY)
            }
        }
    }

    fun clearFilter() {
        filter.value = hashSetOf()
    }

    fun favoritesClicked() {
        showOnlyFavorites.value = !showOnlyFavorites.value
        loadLocations()
    }

    fun filterClicked(locationFeatureId: String, checked: Boolean) {
        val locationFeature = LocationFeature.valueOf(locationFeatureId)
        if (checked) {
            filter.value.add(locationFeature)
        } else {
            filter.value.remove(locationFeature)
        }
    }

    fun onLocationClicked(locationId: String) {
        val location: Location = locations.value.find { it.id == locationId }!!
        mapCenter = LatLng(location.latitude, location.longitude)
        activeLocation = location
        savedStateHandle[ACTIVE_LOCATION_STATE_KEY] = locationId
    }
}