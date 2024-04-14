package com.mariomanhique.quintadoeden.presentation.screens.roomDetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.roomDetails.RoomDetailsScreen

const val roomDetailsRoute = "roomDetailsRoute"

fun NavController.navigateToRoomDetails(){
    navigate(route = roomDetailsRoute)
}

fun NavGraphBuilder.roomDetailsRoute(){
    composable(route = roomDetailsRoute){
        RoomDetailsScreen()
    }
}