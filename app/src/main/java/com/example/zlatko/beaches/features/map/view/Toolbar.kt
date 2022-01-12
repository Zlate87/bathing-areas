package com.example.zlatko.beaches.features.map.view

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.zlatko.beaches.R
import com.example.zlatko.beaches.features.map.viewmodel.MapsViewModel

@Composable
internal fun Toolbar(mapsViewModel: MapsViewModel) {
    val showOnlyFavorites = mapsViewModel.showOnlyFavorites.value
    var showFilter by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(onClick = { mapsViewModel.favoritesClicked() }) {
                Icon(
                    if (showOnlyFavorites)
                        painterResource(R.drawable.ic_favorite)
                    else
                        painterResource(R.drawable.ic_not_favorite),
                    stringResource(id = R.string.favorites)
                )
            }
            IconButton(onClick = { showFilter = !showFilter }) {
                Icon(painterResource(R.drawable.ic_filter), stringResource(id = R.string.filter))
            }
        }
    )
    if (showFilter) {
        Filter(mapsViewModel) {
            mapsViewModel.loadLocations()
            showFilter = false
        }
    }
}