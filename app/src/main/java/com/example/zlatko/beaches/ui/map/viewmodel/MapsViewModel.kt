package com.example.zlatko.beaches.ui.map.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.domain.GetBathingAreasUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBathingAreasUseCase: GetBathingAreasUseCase
) : ViewModel() {

    companion object {
        private val BERLIN_COORDINATES = LatLng(52.520008, 13.404954)
        private const val ACTIVE_LOCATION_STATE_KEY = "ACTIVE_LOCATION_STATE_KEY"
    }

    var mapCenter = BERLIN_COORDINATES
    var activeBathingAreas: BathingAreas? = null
    val locations = mutableStateOf(emptyList<BathingAreas>())
    val showOnlyFavorites = mutableStateOf(false)
    val filter = mutableStateOf(hashSetOf<BathingAreas.Feature>())

    init {
        loadLocations()
    }

    fun loadLocations() {
        val onlyIfFavorite = showOnlyFavorites.value
        val withFeatures = filter.value
        viewModelScope.launch(Dispatchers.IO) {
            locations.value = getBathingAreasUseCase.getAllBathingAreas(withFeatures, onlyIfFavorite)

            if (savedStateHandle.get<String>(ACTIVE_LOCATION_STATE_KEY) != activeBathingAreas?.id) {
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
        val bathingAreasFeature = BathingAreas.Feature.valueOf(locationFeatureId)
        if (checked) {
            filter.value.add(bathingAreasFeature)
        } else {
            filter.value.remove(bathingAreasFeature)
        }
    }

    fun onLocationClicked(locationId: String) {
        val bathingAreas: BathingAreas = locations.value.find { it.id == locationId }!!
        mapCenter = LatLng(bathingAreas.latitude, bathingAreas.longitude)
        activeBathingAreas = bathingAreas
        savedStateHandle[ACTIVE_LOCATION_STATE_KEY] = locationId
    }
}