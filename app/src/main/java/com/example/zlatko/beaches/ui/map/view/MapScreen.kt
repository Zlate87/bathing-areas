package com.example.zlatko.beaches.ui.map.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.zlatko.beaches.ui.map.viewmodel.MapsViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@Composable
internal fun MapScreen(
    mapsViewModel: MapsViewModel,
    onLocationClicked: (locationId: String) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Toolbar(mapsViewModel)
            MapView(onLocationClicked, mapsViewModel)
        }
    }
}