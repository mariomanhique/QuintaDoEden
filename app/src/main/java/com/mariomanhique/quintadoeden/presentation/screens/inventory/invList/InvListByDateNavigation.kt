package com.mariomanhique.quintadoeden.presentation.screens.inventory.invList

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mariomanhique.quintadoeden.util.Constants.LOCAL_DATE

const val invListByDateRoute = "invListByDateRoute"
fun NavController.navigateByDateInv(localDate: String){
    navigate("$invListByDateRoute/$localDate")
}


fun NavGraphBuilder.invListByDateRoute(
){
    composable(
        route = "$invListByDateRoute/{$LOCAL_DATE}",
        arguments = listOf(navArgument(name = LOCAL_DATE){
            type = NavType.StringType
            nullable = true
        })
        ){
        InvListByDateScreen(
        )
    }
}