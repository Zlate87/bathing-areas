package com.example.zlatko.beaches.ui.map.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zlatko.beaches.R
import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.ui.map.viewmodel.MapsViewModel

@Composable
internal fun Filter(
    mapsViewModel: MapsViewModel,
    whenDone: () -> Unit
) {

    AlertDialog(
        onDismissRequest = whenDone,
        title = {
            Text(
                text = stringResource(id = R.string.services),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        },
        text = {
            LazyColumn(modifier = Modifier.padding(4.dp)) {
                val filter = mapsViewModel.filter
                items(BathingAreas.Feature.values()) { feature ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var isActive by remember { mutableStateOf(filter.value.contains(feature)) }
                        Text(
                            stringResource(id = feature.text),
                            Modifier.padding(4.dp),
                            fontSize = 16.sp
                        )
                        Switch(
                            checked = isActive,
                            modifier = Modifier.size(40.dp),
                            onCheckedChange = {
                                isActive = !isActive
                                mapsViewModel.filterClicked(feature.name, isActive)
                            })
                    }
                }
            }
        },
        dismissButton = {
            Button(onClick = {
                mapsViewModel.clearFilter()
                whenDone()
                }) {
                Text(text = stringResource(id = R.string.clear))
            }
        },
        confirmButton = {
            Button(onClick = whenDone) {
                Text(text = stringResource(id = R.string.done))
            }
        }
    )
}