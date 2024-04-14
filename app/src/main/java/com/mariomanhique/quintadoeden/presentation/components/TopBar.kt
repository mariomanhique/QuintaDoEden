package com.mariomanhique.quintadoeden.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
     title: String? = null,
    onActionClicked: () -> Unit ={},
    accountIcon: ImageVector? = null,
    navIcon: ImageVector? = null,
    actionIcon: ImageVector = Icons.Filled.Notifications,
    popBackStack: () -> Unit
) {
    TopAppBar(
        title = {
            Row {
                if (accountIcon != null){
                    Icon(
                        imageVector = accountIcon,
                        contentDescription = "")
                }

                if (title != null) {
                    Text(
                        text = title,
                        fontSize = 18.sp
                    )
                }
            }

    }, actions = {
        IconButton(onClick = onActionClicked) {
            Icon(
                imageVector = actionIcon,
                contentDescription = ""
            )
        }
        },
        navigationIcon = {
            if (navIcon != null){
                IconButton(onClick = {
                    popBackStack()
                }) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = ""
                    )
                }
            }
        })
}