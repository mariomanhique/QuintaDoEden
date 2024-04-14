package com.mariomanhique.quintadoeden.presentation.screens.events.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.events.EventsScreen
import com.mariomanhique.quintadoeden.presentation.screens.notes.NotesScreen

const val eventsRoute = "eventsRoute"


fun NavController.navigateToEvents( navOptions: NavOptions?=null){
    navigate(eventsRoute,navOptions)
}


fun NavGraphBuilder.eventsNavigationRoute(){
    composable(route = eventsRoute){
        EventsScreen()
    }
}