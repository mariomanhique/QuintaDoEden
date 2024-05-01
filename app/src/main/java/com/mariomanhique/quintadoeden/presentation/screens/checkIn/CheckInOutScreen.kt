package com.mariomanhique.quintadoeden.presentation.screens.checkIn

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Checkbox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.presentation.components.GuestCard
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.Guest
import com.mariomanhique.quintadoeden.model.guestList

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckInOutScreen(
    popBackStack: () -> Unit,
    checkInViewModel: CheckInViewModel = hiltViewModel()
) {
    val tabItems = listOf(
        TabItem(
            title = "Entradas",
//            selectedIcon = Icons.Filled.Place,
//            unSelectedIcon = Icons.Outlined.Place
        ),
        TabItem(
            title = "Saídas",
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = stringResource(id = R.string.check_in),
            navIcon = Icons.Filled.ArrowBack,
            textAction = "Adicionar Quarto",
            popBackStack = popBackStack
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
                .fillMaxWidth()
                .weight(1f)
        ) { index->

            when(index){
                0 -> CheckInOutContent(guestList)
                1 -> CheckInOutContent(emptyList())
            }
        }


    }
}

@Composable
fun CheckInOutContent(
    guests:List<Guest>
) {
    LazyColumn {
        items(items = guests){
            CheckInOutCard(
                roomType = it.room.roomType,
                roomNr = it.room.roomNr,
                isCheckIn = it.isCheckIn,
                roomState = it.room.roomState,
                observations = it.observations,
                onCheckedChange = {

                }
            )
        }
    }
}

@Composable
fun CheckInOutCard(
    roomType: String,
    roomNr: String,
    roomState: String,
    isCheckIn: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    observations: List<String>,
) {

    var checkState by remember {
        mutableStateOf(isCheckIn)
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "$roomNr. $roomType")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            Text(text = "Entrada/Saida")

            Checkbox(
                checked = checkState,
                onCheckedChange = {
                    checkState = it
                    onCheckedChange(it)
                }
            )
        }
    }
}

data class TabItem(
    val title: String,
//     val unSelectedIcon: ImageVector,
//     val selectedIcon: ImageVector
)