package com.example.zlatko.beaches.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.zlatko.beaches.ui.details.view.DetailsScreen

internal const val DETAILS_SCREEN_ROUTE = "DetailsScreen"
internal const val BATHING_AREA_ID = "locationId"

@ExperimentalCoilApi
internal fun NavGraphBuilder.detailsScreenComposable(startActivity: (url: String) -> Unit) {
    composable(
        route = "$DETAILS_SCREEN_ROUTE/{$BATHING_AREA_ID}",
        arguments = listOf(navArgument(BATHING_AREA_ID) {
            type = NavType.StringType
        })
    ) {
        DetailsScreen(
            startActivity = startActivity
        )
    }
}