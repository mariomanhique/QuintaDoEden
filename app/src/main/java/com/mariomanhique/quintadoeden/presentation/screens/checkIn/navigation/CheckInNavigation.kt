package com.mariomanhique.quintadoeden.presentation.screens.checkIn.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.CheckInScreen


const val checkInRoute = "checkInRoute"

fun NavController.navigateToCheckIn(){
    navigate(route = checkInRoute)
}

fun NavGraphBuilder.checkInRoute(
    popBackStack: () -> Unit
){
    composable(route = checkInRoute){
        CheckInScreen(
            popBackStack = popBackStack
        )
    }
}