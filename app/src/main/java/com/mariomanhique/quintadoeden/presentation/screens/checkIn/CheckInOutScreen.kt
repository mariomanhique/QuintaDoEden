package com.mariomanhique.quintadoeden.presentation.screens.checkIn

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.Guest
import com.mariomanhique.quintadoeden.model.Room
import com.mariomanhique.quintadoeden.presentation.screens.rooms.RoomsViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckInOutScreen(
    popBackStack: () -> Unit,
    roomsViewModel: RoomsViewModel = hiltViewModel()
) {
    val cleanRooms by roomsViewModel.items.collectAsStateWithLifecycle()
    val inGuests by roomsViewModel.inGuests.collectAsStateWithLifecycle()
    val guests by roomsViewModel.guests.collectAsStateWithLifecycle()
    val outGuests by roomsViewModel.outGuests.collectAsStateWithLifecycle()

    Log.d("CheckSate", "CheckInOutCard: Outside of OnChange $inGuests")

    val tabItems = listOf(
        TabItem(
            title = "Entradas",
        ),
        TabItem(
            title = "Saídas",
        ),
        TabItem(
            title = "Todos",
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
            title = stringResource(id = R.string.check_in_out),
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
                .weight(1f),
            verticalAlignment = Alignment.Top,
//            key = {
//
//            }
        ) { index->

            when(index){
                0 -> {
                    CheckInOutContent(
                        guests = inGuests,
                        tabIndex = 0,
                        rooms = cleanRooms,
                        onCheckedChange = {
                            roomsViewModel.editGuests(
                                guest = it,
                                onSuccess = {

                                },
                                onError = {

                                }
                            )
                        })
                }
                1 -> {
                    CheckInOutContent(
                    guests = outGuests,
                    tabIndex = 1,
                    rooms = cleanRooms,
                    onCheckedChange = {
                        roomsViewModel.editGuests(
                            guest = it,
                            onSuccess = {

                            },
                            onError = {

                            }
                        )
                    })
                }
                2 -> {
                    CheckInOutContent(
                        guests = guests,
                        tabIndex = 2,
                        rooms = cleanRooms,
                        onCheckedChange = {
                        roomsViewModel.editGuests(
                            guest = it,
                            onSuccess = {

                            },
                            onError = {

                            }
                        )
                        })
                }
            }
        }


    }
}

@Composable
fun CheckInOutContent(
    guests:List<Guest>,
    rooms: List<Room>,
    tabIndex: Int,
    onCheckedChange: (Guest) -> Unit
) {

    LazyColumn {
        items(items = guests){ guest->
                if (rooms.isNotEmpty()){

                    val room = rooms.filter {
                        it.roomNr == guest.roomNr
                    }.first()


                    CheckInOutCard(
                    roomType = room.roomType,
                    roomNr = room.roomNr,
                    tabIndex = tabIndex,
                    isCheckIn = guest.isCheckIn,
                    isCheckOut = guest.isCheckOut,
                    roomState = room.roomState,
                    observations = guest.observations,
                    onVerifiedClicked = {

                    },
                    onCheckedChange = {checkState ,checkBox->
                        onCheckedChange(
                            guest.copy(
                                checkInDate = guest.checkInDate,
                                checkOutDate = guest.checkOutDate,
                                isCheckIn = if (checkBox == "checkin") checkState else false,
                                isCheckOut = if (checkBox == "checkout") checkState else false,
                                roomNr = room.roomNr,
                                //                        observations = guest.observations
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun CheckInOutCard(
    roomType: String,
    roomNr: String,
    tabIndex: Int,
    roomState: String,
    isCheckIn: Boolean,
    isCheckOut: Boolean,
    onVerifiedClicked: () -> Unit,
    onCheckedChange: (Boolean, String) -> Unit,
    observations: List<String>,
) {

    var checkInState by remember {
        mutableStateOf(isCheckIn)
    }

    var checkOutState by remember {
        mutableStateOf(isCheckOut)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$roomNr. $roomType")

            TextButton(onClick = {
                onVerifiedClicked()
            }) {
                Text(
                    text = "Verificar",
                    style = MaterialTheme.typography.titleMedium
                    )
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            if (tabIndex == 2){
                GuestState(
                    value = "Entrada",
                    guestState = checkInState
                ) { checkInValue ->
                    checkInState = checkInValue
                    checkOutState = if (checkInState) false else checkOutState
                    onCheckedChange(checkInValue, "checkin")
                }

                GuestState(
                    value = "Saida",
                    guestState = checkOutState
                ) {
                    checkOutState = it
                    checkInState = if (checkOutState) false else checkInState
                    onCheckedChange(it, "checkout")
                }
            }
        }
        Divider()
    }
}

@Composable
fun GuestState(
    value: String,
    guestState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = value)
        Checkbox(
            checked = guestState,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
    }
}

data class TabItem(
    val title: String,
)