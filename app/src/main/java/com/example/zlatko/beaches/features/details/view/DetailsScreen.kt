package com.example.zlatko.beaches.features.details.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import com.example.zlatko.beaches.features.details.viewmodel.DetailsViewModel


@ExperimentalCoilApi
@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    locationId: String,
    startActivity: (url: String) -> Unit,
) {
    detailsViewModel.loadLocation(locationId)
    val location = detailsViewModel.location.value
    Surface {
        Column {
            Toolbar(detailsViewModel, startActivity)
            DetailsView(location)
        }
    }
}