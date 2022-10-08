package com.example.zlatko.beaches.ui.map.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.zlatko.beaches.R
import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.ui.map.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@Composable
internal fun MapView2(
    onLocationClicked: (locationId: String) -> Unit,
    mapsViewModel: MapsViewModel
) {
    val locations = mapsViewModel.locations.value
    val mapView = rememberMapViewWithLifecycle()
    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync { map ->
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    mapsViewModel.mapCenter,
                    7.6F
                )
            )

            val locationsIds = HashMap<Marker, String>()

            map.setOnInfoWindowClickListener {
                GlobalScope.launch(Dispatchers.Main) {
                    onLocationClicked(locationsIds[it]!!)
                }
            }

            displayLocations(map, locations, locationsIds, mapsViewModel.activeBathingAreas)
        }
    }
}

private fun displayLocations(
    map: GoogleMap,
    bathingAreas: List<BathingAreas>,
    locationsIds: HashMap<Marker, String>,
    activeBathingAreas: BathingAreas?
) {
    map.clear()
    val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_swim)
    bathingAreas.forEach { location ->
        val markerOptions = MarkerOptions()
            .position(LatLng(location.latitude, location.longitude))
            .title(location.name)
            .icon(icon)
        val marker = map.addMarker(markerOptions)!!
        if (location.id == activeBathingAreas?.id) {
            marker.showInfoWindow()
        }
        locationsIds[marker] = location.id
    }

}