package com.example.zlatko.beaches.features.details.view

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.zlatko.beaches.R
import com.example.zlatko.beaches.features.details.viewmodel.DetailsViewModel

@Composable
internal fun Toolbar(
    detailsViewModel: DetailsViewModel,
    startActivity: (url: String) -> Unit
) {
    val location = detailsViewModel.location.value
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = location?.name ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(onClick = { detailsViewModel.favoriteToggleClicked() }) {
                Icon(
                    if (location?.favorite == true)
                        painterResource(R.drawable.ic_favorite)
                    else
                        painterResource(R.drawable.ic_not_favorite),
                    stringResource(id = R.string.favorites)
                )
            }
            IconButton(onClick = {
                startActivity(detailsViewModel.prepareUrlForNavigating())
            }) {
                Icon(
                    painterResource(R.drawable.ic_navigation),
                    stringResource(id = R.string.navigate)
                )
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, stringResource(id = R.string.more))
            }
            DropdownMenu(
                expanded = showMenu,
                offset = DpOffset((0).dp, 0.dp),
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = {
                    startActivity(detailsViewModel.prepareUrlForMoreDetails())
                }) {
                    Text(text = stringResource(id = R.string.more))
                }
            }
        }
    )
}