package com.example.zlatko.beaches.ui.details.view

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
import com.example.zlatko.beaches.data.BathingAreas

@Composable
internal fun Toolbar(
    bathingArea: BathingAreas,
    favoritesClicked: () -> Unit,
    navigationClicked: () -> Unit,
    moreDetailsClicked: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = bathingArea.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(onClick = favoritesClicked) {
                Icon(
                    if (bathingArea.favorite)
                        painterResource(R.drawable.ic_favorite)
                    else
                        painterResource(R.drawable.ic_not_favorite),
                    stringResource(id = R.string.favorites)
                )
            }
            IconButton(onClick = navigationClicked) {
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
                DropdownMenuItem(onClick = moreDetailsClicked) {
                    Text(text = stringResource(id = R.string.more))
                }
            }
        }
    )
}