package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mariomanhique.quintadoeden.util.Constants.CATEGORY_ARG
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.OrderedByDateScreen
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.SubmittedCategoriesScreen

const val submittedInventoryRoute = "submittedInventoryRoute"
const val S_INV_GRAPH_ROUTE_PATTERN = "submittedInventoryGraph"



fun NavController.navigateToSubGraph(navOptions: NavOptions?=null){
    navigate(S_INV_GRAPH_ROUTE_PATTERN, navOptions)
}



fun NavGraphBuilder.submittedInventoryRoute(
    navigateToSavedInveWithArgs: (String) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,

    ){

    navigation(
        startDestination = submittedInventoryRoute,
        route =S_INV_GRAPH_ROUTE_PATTERN
    ){
        composable(route = submittedInventoryRoute){
            SubmittedCategoriesScreen(
                categoryClicked = navigateToSavedInveWithArgs
            )
        }

        nestedGraph()
    }

}





/////////////////////////////////////////////////////

const val byDateRoute = "byDateRoute"

fun NavController.navigateToSOrderByDate(category: String){
    navigate("$byDateRoute/$category")
}



fun NavGraphBuilder.byDateRoute(
    navigateToOrderedList: (String) -> Unit
){
    composable(route = "$byDateRoute/{$CATEGORY_ARG}",
        arguments = listOf(navArgument(name = CATEGORY_ARG){
            type = NavType.StringType
            nullable = true
        })
        ){

        OrderedByDateScreen(navigateToOrderedList =navigateToOrderedList)
    }
}