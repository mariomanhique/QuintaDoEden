package com.mariomanhique.quintadoeden.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signInWithCredencials.navigation.navigateToSignIn
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signInWithCredencials.navigation.signInNavigationRoute
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signInWithCredencials.navigation.signInRoute
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signUpWithCredentials.navigation.navigateToSignUp
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signUpWithCredentials.navigation.signUpRoute
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.navigation.checkInRoute
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.navigation.navigateToCheckIn
import com.mariomanhique.quintadoeden.presentation.screens.cleaning.navigation.cleaningRoute
import com.mariomanhique.quintadoeden.presentation.screens.cleaning.navigation.navigateToCleaning
import com.mariomanhique.quintadoeden.presentation.screens.events.navigation.eventsNavigationRoute
import com.mariomanhique.quintadoeden.presentation.screens.guestDetails.navigation.guestDetailsRoute
import com.mariomanhique.quintadoeden.presentation.screens.home.navigation.homeRoute
import com.mariomanhique.quintadoeden.presentation.screens.home.navigation.navigateToHome
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.navigation.drinksInvRoute
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.navigation.fillDrinksInvRoute
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.navigation.navigateToDrinksInv
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.navigation.navigateToFillDrinksInv
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.byDateRoute
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.navigateToSOrderByDate
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.submittedInventoryRoute
import com.mariomanhique.quintadoeden.presentation.screens.notes.navigation.notesNavigationRoute
import com.mariomanhique.quintadoeden.presentation.screens.roomDetails.navigation.navigateToRoomDetails
import com.mariomanhique.quintadoeden.presentation.screens.roomDetails.navigation.roomDetailsRoute
import com.mariomanhique.quintadoeden.ui.AppState

@Composable
fun QDENavHost(
    startDestination: String,
    appState: AppState
) {


    val navController = appState.navController

    NavHost(navController = navController, startDestination = startDestination){
        signUpRoute(onShowSnackbar = {s,q ->
            true
        },
            isNetworkAvailable = true,
            navigateToSignIn = navController::navigateToSignIn,
            navigateToHome = { appState.navigateToTopLevelDestination(TopLevelDestination.HOME) }
        )

        signInRoute(onShowSnackbar = {s,q ->
            true
        },
            isNetworkAvailable = true,
            navigateToSignUp = navController::navigateToSignUp,
            navigateToHome = { appState.navigateToTopLevelDestination(TopLevelDestination.HOME) }
        )

        homeRoute(
            navigateToCleaning = navController::navigateToCleaning,
            navigateToCheckIn = navController::navigateToCheckIn,
            navigateToDrinksInv = navController::navigateToDrinksInv
        )

        cleaningRoute(
            popBackStack = navController::popBackStack,
            onRoomSelected = navController::navigateToRoomDetails
        )

        checkInRoute(
            popBackStack = navController::popBackStack
        )

        roomDetailsRoute()

        guestDetailsRoute()

        drinksInvRoute(
            popBackStack = navController::popBackStack,
            onAddInventoryClicked = {},
            navigateToFillInveWithArgs = {
                navController.navigateToFillDrinksInv(it)
            }
        )
        fillDrinksInvRoute(
            popBackStack = navController::popBackStack,
            onAddInventoryClicked = {}
        )

        submittedInventoryRoute(
           navigateToSavedInveWithArgs = {
               navController.navigateToSOrderByDate(it)
           },
            nestedGraph = {
                byDateRoute()
            }
        )

        notesNavigationRoute()

        eventsNavigationRoute()



    }

}