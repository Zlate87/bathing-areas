package com.example.zlatko.beaches.base.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.zlatko.beaches.features.details.view.DetailsScreen
import com.example.zlatko.beaches.features.details.viewmodel.DetailsViewModel

internal const val DETAILS_SCREEN_ROUTE = "DetailsScreen"

@ExperimentalCoilApi
internal fun NavGraphBuilder.detailsScreenComposable(startActivity: (url: String) -> Unit) {
    composable(
        route = "$DETAILS_SCREEN_ROUTE/{locationId}",
        arguments = listOf(navArgument("locationId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val detailsViewModel: DetailsViewModel = hiltViewModel()
        val locationId = navBackStackEntry.arguments!!.getString("locationId")!!
        DetailsScreen(
            detailsViewModel = detailsViewModel,
            locationId = locationId,
            startActivity = startActivity
        )
    }
}