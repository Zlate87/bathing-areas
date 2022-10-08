package com.example.zlatko.beaches.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@Composable
fun AppNavigation(
    startActivity: (url: String) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MAPS_ROUTE) {
        mapsScreenComposable(navController)
        detailsScreenComposable(startActivity)
    }
}