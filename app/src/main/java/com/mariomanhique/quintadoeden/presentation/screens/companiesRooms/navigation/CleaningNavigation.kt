package com.mariomanhique.quintadoeden.presentation.screens.companiesRooms.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.companiesRooms.CompaniesRoomsScreen

const val companiesCleaningRoute = "companiesCleaningRoute"

fun NavController.navigateToCompaniesRooms(){
    navigate(route = companiesCleaningRoute)
}

fun NavGraphBuilder.companiesRoute(
    popBackStack: () -> Unit,
    onRoomSelected: ()-> Unit
){
    composable(route = companiesCleaningRoute){
        CompaniesRoomsScreen(
            popBackStack = popBackStack,
            onRoomSelected = onRoomSelected
        )
    }
}