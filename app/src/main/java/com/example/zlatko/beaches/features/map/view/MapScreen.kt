package com.example.zlatko.beaches.features.map.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.zlatko.beaches.features.map.viewmodel.MapsViewModel

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