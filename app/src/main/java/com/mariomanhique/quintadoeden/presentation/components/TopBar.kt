package com.mariomanhique.quintadoeden.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String? = null,
    onActionClicked: () -> Unit ={},
    onTextActionClicked: () -> Unit ={},
    accountIcon: ImageVector? = null,
    textAction: String? = null,
    navIcon: ImageVector? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actionIcon: ImageVector? = null,
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
                        style = MaterialTheme.typography.titleMedium.copy(
                        )
                    )
                }
            }

    },
        scrollBehavior = scrollBehavior,
        actions = {

        if (textAction != null) {
            TextButton(onClick = onTextActionClicked) {
                Text(text = textAction,
                    style = MaterialTheme.typography.titleMedium.copy(
                    )
                )
            }
        }


        if (actionIcon != null) {
                IconButton(onClick = onActionClicked) {

                Icon(
                    imageVector = actionIcon,
                    contentDescription = ""
                )
             }
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