package com.mariomanhique.quintadoeden.presentation.screens.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.home.HomeScreen

const val homeRoute = "homeRoute"

fun NavController.navigateToHome(navOptions: NavOptions?=null){
    navigate(homeRoute, navOptions)
}


fun NavGraphBuilder.homeRoute(
    navigateToCleaning: () -> Unit,
    navigateToCheckIn: () -> Unit,
    navigateToDrinksInv: () -> Unit,
    navigateToCompaniesRooms: () -> Unit,
    ){
   composable(route = homeRoute){
       HomeScreen(
           navigateToCleaning = navigateToCleaning,
           navigateToCheckIn = navigateToCheckIn,
           onDrinksInventoryClicked = navigateToDrinksInv,
           navigateToCompaniesRooms = navigateToCompaniesRooms
       )
   }
}