package com.mariomanhique.quintadoeden.presentation.screens.companiesRooms

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.TabItem
import com.mariomanhique.quintadoeden.presentation.screens.rooms.RoomsContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CompaniesRoomsScreen(
    popBackStack: () -> Unit,
    onRoomSelected: ()-> Unit,
    companiesViewModel: CompaniesRoomsViewModel = hiltViewModel()
) {

    val cleanedRooms by companiesViewModel.cleanedRooms.collectAsStateWithLifecycle()
    val unCleanedRooms by companiesViewModel.unCleanedRooms.collectAsStateWithLifecycle()

    val tabItems = listOf(
        TabItem(
            title = "Sujos",
//            selectedIcon = Icons.Filled.Place,
//            unSelectedIcon = Icons.Outlined.Place
        ),
        TabItem(
            title = "Limpos",
//            selectedIcon = Icons.Filled.ArrowForward,
//            unSelectedIcon = Icons.Outlined.ArrowForward
        )
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    //This helps
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress){
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = stringResource(id = R.string.cleaning),
            navIcon = Icons.Filled.ArrowBack,
            popBackStack = popBackStack,
            textAction = "Histórico",
            onTextActionClicked = {

            }
        )
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(text = tabItem.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 19.sp
                            )
                        )
                    })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(vertical = 4.dp)
        ) { index->
            when(index){
                0 -> {
                    RoomsContent(
                        onCleanSelected = {
                            companiesViewModel.editRoomCleanState(
                                roomState = it.roomState,
                                roomNr = it.roomNr,
                                onSuccess = {},
                                onError = {}
                            )
                        },
                        onDirtySelected = {
                            companiesViewModel.editRoomCleanState(
                                roomState = it.roomState,
                                roomNr = it.roomNr,
                                onSuccess = {},
                                onError = {}
                            )
                        },
                        rooms = unCleanedRooms
                    )
                }
                1 -> {
                    RoomsContent(
                        onCleanSelected = {
                            companiesViewModel.editRoomCleanState(
                                roomState = it.roomState,
                                roomNr = it.roomNr,
                                onSuccess = {},
                                onError = {}
                            )
                        },
                        onDirtySelected = {
                            companiesViewModel.editRoomCleanState(
                                roomState = it.roomState,
                                roomNr = it.roomNr,
                                onSuccess = {},
                                onError = {}
                            )
                        },
                        rooms = cleanedRooms
                    )
                }
            }
        }
    }
}