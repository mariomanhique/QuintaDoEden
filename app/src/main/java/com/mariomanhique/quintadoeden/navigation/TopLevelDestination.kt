package com.mariomanhique.quintadoeden.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.mariomanhique.quintadoeden.R

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {

    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home,
        titleTextId = R.string.home

    ),
    INVENTORY(
        selectedIcon = Icons.Rounded.Assignment,
        unselectedIcon = Icons.Outlined.Assignment,
        iconTextId = R.string.inventory,
        titleTextId =R.string.inventory

    ),
    NOTES(
        selectedIcon = Icons.Rounded.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
        iconTextId = R.string.notes,
        titleTextId = R.string.notes

    ),

    EVENTS(
        selectedIcon = Icons.Rounded.Event,
        unselectedIcon = Icons.Outlined.Event,
        iconTextId = R.string.events,
        titleTextId = R.string.events
    )
}