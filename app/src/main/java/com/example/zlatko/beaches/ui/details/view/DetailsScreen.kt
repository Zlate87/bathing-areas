package com.example.zlatko.beaches.ui.details.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.example.zlatko.beaches.R
import com.example.zlatko.beaches.ui.details.viewmodel.DetailsViewModel


@ExperimentalCoilApi
@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    startActivity: (url: String) -> Unit,
) {
    val viewStateMutable = detailsViewModel.viewState.observeAsState()
    Surface {
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            when (val viewState = viewStateMutable.value) {
                is DetailsViewModel.ViewState.Content -> {
                    val bathingAreas = viewState.bathingArea
                    Toolbar(
                        bathingArea = bathingAreas,
                        favoritesClicked = {
                            detailsViewModel.favoriteToggleClicked()
                        },
                        moreDetailsClicked = {
                            detailsViewModel.prepareUrlForMoreDetails()?.let { startActivity(it) }
                        },
                        navigationClicked = {
                            startActivity(detailsViewModel.prepareUrlForNavigating())
                        }
                    )
                    DetailsView(bathingAreas)
                }
                DetailsViewModel.ViewState.Loading -> Text(stringResource(R.string.loading))
                else -> Text(text = stringResource(R.string.error))
            }
        }
    }
}