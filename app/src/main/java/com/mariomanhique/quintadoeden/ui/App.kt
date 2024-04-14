package com.mariomanhique.quintadoeden.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.navigation.QDENavHost
import com.mariomanhique.quintadoeden.navigation.TopLevelDestination
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signInWithCredencials.navigation.signInNavigationRoute
import com.mariomanhique.quintadoeden.presentation.screens.home.navigation.homeRoute
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.navigation.byDateRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun App(
    windowSizeClass: WindowSizeClass,
    appState: AppState = rememberAppState(
        windowSizeClass = windowSizeClass
    )
){

    val snackbarHostState = remember { SnackbarHostState() }
    val destination = appState.currentTopLevelDestination
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var dialogState by remember {
        mutableStateOf(false)
    }

//    if (dialogState){
//        CustomDialog(
//            title = R.string.signOut,
//            text = R.string.signout_confirmation ,
//            onDismissDialog = {
//                dialogState = false
//            },
//            confirmButton = {
//                FirebaseAuth.getInstance().signOut()
//                isSheetOpen = false
//                if (FirebaseAuth.getInstance().currentUser == null){
//                    dialogState = false
//                }
//            }
//        )
//    }

//    if (isSheetOpen){
//        MenuSheet(
//            onSheetDismissed = {
//                isSheetOpen = false
//            },
//            onSignedOut = {
//                if (FirebaseAuth.getInstance().currentUser != null){
//                    dialogState = true
//                }
//            },
//            onSignedIn = {
//                appState.navigateToSignIn()
//                isSheetOpen = false
//            }
//        )
//    }
    Scaffold(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            }
            .background(MaterialTheme.colorScheme.surface),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (
                destination != null){
                TopBar(title = stringResource(id = destination.iconTextId)) {

                }
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                if (destination != null) {
                    AppBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier.testTag("QDEBottomBar"),
                    )
                }
            }
        }){paddingValues->
        Row(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            if (appState.shouldShowNavRail) {
                if (destination != null) {
                    AppNavRail(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier
                            .testTag("DokkhotaNavRail")
                            .safeDrawingPadding(),
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                QDENavHost(
                    startDestination = if (FirebaseAuth.getInstance().currentUser == null) signInNavigationRoute else homeRoute,
                    appState = appState
                )
            }
        }


    }

}


@Composable
fun AppBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
){
    AppNavigationBar(
        modifier = modifier
    ) {

        destinations.forEach {destination->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            Log.d("Test Nav", "NiaBottomBar:$destination")
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )

        }
    }

}


@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = Modifier,
        contentColor = AppNavigationDefaults.navigationContentColor(),
        tonalElevation = 3.dp,
        content = content,
    )
}


@Composable
fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = false,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = AppNavigationDefaults.navigationContentColor(),
            selectedTextColor = AppNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = AppNavigationDefaults.navigationContentColor(),
            indicatorColor = AppNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}


object AppNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}


@Composable
private fun AppNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun AppNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = AppNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = AppNavigationDefaults.navigationContentColor(),
            selectedTextColor = AppNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = AppNavigationDefaults.navigationContentColor(),
            indicatorColor = AppNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = AppNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false


