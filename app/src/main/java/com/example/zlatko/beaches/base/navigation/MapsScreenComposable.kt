package com.example.zlatko.beaches.base.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.zlatko.beaches.features.map.view.MapScreen
import com.example.zlatko.beaches.features.map.viewmodel.MapsViewModel

internal const val MAPS_ROUTE = "MapsScreen"

internal fun NavGraphBuilder.mapsScreenComposable(navController: NavHostController) {
    composable(route = MAPS_ROUTE) {
        val mapsViewModel: MapsViewModel = hiltViewModel()
        MapScreen(mapsViewModel) { locationId ->
            mapsViewModel.onLocationClicked(locationId)
            navController.navigate("$DETAILS_SCREEN_ROUTE/${locationId}")
        }
    }
}