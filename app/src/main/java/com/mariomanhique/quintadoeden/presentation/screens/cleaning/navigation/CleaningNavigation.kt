package com.mariomanhique.quintadoeden.presentation.screens.cleaning.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.rooms.RoomsScreen

const val cleaningRoute = "cleaningRoute"

fun NavController.navigateToCleaning(){
    navigate(route = cleaningRoute)
}

fun NavGraphBuilder.cleaningRoute(
    popBackStack: () -> Unit,
    onRoomSelected: ()-> Unit
){
    composable(route = cleaningRoute){
        RoomsScreen(
            popBackStack = popBackStack,
            onRoomSelected = onRoomSelected
        )
    }
}

///////////////////////////////////////// History Graph ///////////
