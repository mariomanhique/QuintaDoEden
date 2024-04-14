package com.mariomanhique.quintadoeden.presentation.screens.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.quintadoeden.presentation.screens.notes.NotesScreen

const val notesNavigationRoute = "notesNavigationRoute"

fun NavController.navigateToNotes(navOptions: NavOptions?=null){
    navigate(notesNavigationRoute, navOptions)
}


fun NavGraphBuilder.notesNavigationRoute(){
     composable(route = notesNavigationRoute){
         NotesScreen()
     }
}