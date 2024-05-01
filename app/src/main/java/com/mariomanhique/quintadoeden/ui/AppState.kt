package com.mariomanhique.quintadoeden.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.mariomanhique.quintadoeden.util.Constants.CATEGORY_ARG
import com.mariomanhique.quintadoeden.util.Constants.LOCAL_DATE
import com.mariomanhique.quintadoeden.navigation.TopLevelDestination
import com.mariomanhique.quintadoeden.presentation.screens.events.navigation.eventsRoute
import com.mariomanhique.quintadoeden.presentation.screens.events.navigation.navigateToEvents
import com.mariomanhique.quintadoeden.presentation.screens.home.navigation.homeRoute
import com.mariomanhique.quintadoeden.presentation.screens.home.navigation.navigateToHome
import com.mariomanhique.quintadoeden.presentation.screens.inventory.invList.invListByDateRoute
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.byDateRoute
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.navigateToSubGraph
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.submittedInventoryRoute
import com.mariomanhique.quintadoeden.presentation.screens.notes.navigation.navigateToNotes
import com.mariomanhique.quintadoeden.presentation.screens.notes.navigation.notesNavigationRoute
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    ): AppState {
    return AppState(
        navController,
        windowSizeClass,
        coroutineScope,
    )
}

class AppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass,
    private val coroutineScope: CoroutineScope,
){

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> TopLevelDestination.HOME
            submittedInventoryRoute -> TopLevelDestination.INVENTORY
            notesNavigationRoute -> TopLevelDestination.NOTES
            eventsRoute -> TopLevelDestination.EVENTS
            else -> null
        }


    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

//    val isOffline = connectivity.observe()
//        .map{
//            it
//        }.stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = ConnectivityObserver.Status.Lost
//        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries



    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}"){
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }

                launchSingleTop = true

                restoreState = true
            }

            when(topLevelDestination){
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.INVENTORY -> navController.navigateToSubGraph(topLevelNavOptions)
                TopLevelDestination.NOTES -> navController.navigateToNotes(topLevelNavOptions)
                TopLevelDestination.EVENTS -> navController.navigateToEvents(topLevelNavOptions)
            }
        }

    }


//    fun navigateToSignIn(){
//        navController.navigateToSignIn()
//    }

}