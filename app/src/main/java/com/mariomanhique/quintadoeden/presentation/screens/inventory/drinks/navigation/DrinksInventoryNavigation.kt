package com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mariomanhique.quintadoeden.Util.Constants.CATEGORY_ARG
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.DrinksInventoryScreen
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.FillDrinksInventoryScreen

const val drinksInvRoute = "drinksInvRoute"


fun NavController.navigateToDrinksInv(){
    navigate(route = drinksInvRoute)
}

fun NavGraphBuilder.drinksInvRoute(
    popBackStack: () -> Unit,
    onAddInventoryClicked: () -> Unit,
    navigateToFillInveWithArgs: (String) -> Unit
){
    composable(route = drinksInvRoute){
        DrinksInventoryScreen(
            popBackStack = popBackStack,
            onAddInventoryClicked = onAddInventoryClicked,
            categoryClicked = navigateToFillInveWithArgs
        )
    }
}
///////////////////////////////////////////

const val fillDrinksInvRoute = "fillDrinksInvRoute"

fun NavController.navigateToFillDrinksInv(category: String){
    navigate(route = "$fillDrinksInvRoute/$category")
}

fun NavGraphBuilder.fillDrinksInvRoute(
    popBackStack: () -> Unit,
    onAddInventoryClicked: () -> Unit
){
    composable(route = "$fillDrinksInvRoute/{$CATEGORY_ARG}",
        arguments = listOf(navArgument(name = CATEGORY_ARG){
            type = NavType.StringType
            nullable = true
        })
        ){
        FillDrinksInventoryScreen(
//            popBackStack = popBackStack,
//            onAddInventoryClicked = onAddInventoryClicked
        )
    }
}