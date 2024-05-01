package com.mariomanhique.quintadoeden.presentation.screens.companiesRooms.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.cleaning.CleaningContent
import com.mariomanhique.quintadoeden.presentation.screens.cleaning.CleaningScreen
import com.mariomanhique.quintadoeden.presentation.screens.companiesRooms.CompaniesRoomsScreen
import com.mariomanhique.quintadoeden.presentation.screens.rooms.RoomsScreen

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