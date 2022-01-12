package com.example.zlatko.beaches.features.details.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zlatko.beaches.base.service.location.LocationService
import com.example.zlatko.beaches.base.service.location.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val locationService: LocationService
) : ViewModel() {

    val location = mutableStateOf<Location?>(null)

    fun loadLocation(name: String) {
        viewModelScope.launch {
            location.value = locationService.getLocation(name)
        }
    }

    fun favoriteToggleClicked() {
        location.value = location.value!!.copy(favorite = !location.value!!.favorite)
        viewModelScope.launch {
            locationService.update(location.value!!)
        }
    }

    fun prepareUrlForNavigating(): String {
        val location = location.value!!
        val coordinates = "${location.latitude},${location.longitude}"
        return "geo:$coordinates?q=$coordinates"
    }

    fun prepareUrlForMoreDetails(): String {
        return location.value!!.detailsUrl
    }

}