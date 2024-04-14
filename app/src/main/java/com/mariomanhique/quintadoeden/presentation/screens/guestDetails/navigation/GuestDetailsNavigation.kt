package com.mariomanhique.quintadoeden.presentation.screens.guestDetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.guestDetails.GuestDetailsScreen

const val guestDetailsRoute = "guestDetailsRoute"

fun NavController.navigateToGuestDetails(){
    navigate(route = guestDetailsRoute)
}

fun NavGraphBuilder.guestDetailsRoute(){
    composable(route = guestDetailsRoute){
        GuestDetailsScreen()
    }
}