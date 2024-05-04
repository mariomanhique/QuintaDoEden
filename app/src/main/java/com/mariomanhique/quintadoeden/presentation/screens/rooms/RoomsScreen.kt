package com.mariomanhique.quintadoeden.presentation.screens.rooms

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.Room
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.TabItem


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoomsScreen(
    popBackStack: () -> Unit,
    onRoomSelected: ()-> Unit,
    roomsViewModel: RoomsViewModel = hiltViewModel()
) {

    val cleanRooms by roomsViewModel.items.collectAsStateWithLifecycle()
    val unCleanedRooms by roomsViewModel.unCleanedRooms.collectAsStateWithLifecycle()

    Log.d("Rooms", "CheckInOutContent:$cleanRooms")


    val tabItems = listOf(
        TabItem(
            title = "Sujos",
        ),
        TabItem(
            title = "Limpos",
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
                             roomsViewModel.editRoomCleanState(
                                 roomState = it.roomState,
                                 roomNr = it.roomNr,
                                 onSuccess = {},
                                 onError = {}
                             )
                        },
                        onDirtySelected = {
                            roomsViewModel.editRoomCleanState(
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
                            roomsViewModel.editRoomCleanState(
                                roomState = it.roomState,
                                roomNr = it.roomNr,
                                onSuccess = {},
                                onError = {}
                            )
                        },
                        onDirtySelected = {
                            roomsViewModel.editRoomCleanState(
                                roomState = it.roomState,
                                roomNr = it.roomNr,
                                onSuccess = {},
                                onError = {}
                            )
                        },
                        rooms = cleanRooms
                    )
                }
            }
        }
   }
}

@Composable
fun RoomsContent(
    rooms: List<Room>,
    onCleanSelected: (Room)-> Unit,
    onDirtySelected: (Room)-> Unit
) {

    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top
    ) {
        items(items = rooms){
            RoomCard(
                roomType = it.roomType,
                roomNr = it.roomNr,
                roomState = it.roomState,
                username = it.username,
                roomAvailability = it.roomAvailability,
                onCleanSelected = {

                    onCleanSelected(it.copy(
                    roomState = if (it.roomState == "Sujo") "Limpo" else "Sujo"
                )) },
                onDirtySelected = {
                },
                observations = it.observations
            )
        }
    }

}


@Composable
fun RoomCard(
    roomType: String,
    roomNr: String,
    roomState: String,
    username: String,
    roomAvailability: String,
    observations: List<String>,
    onCleanSelected: ()-> Unit,
    onDirtySelected: ()-> Unit
 ) {
    var dropDownState by remember {
            mutableStateOf(false)
    }

    if (dropDownState){
        DropDown(
            expandDropDown = dropDownState,
            onDismiss = {
                dropDownState = !dropDownState
            },
            onCleanSelected = {
                onCleanSelected()
                dropDownState = false
            },
            onDirtySelected = {
                onDirtySelected()
                dropDownState = false
            }
        )
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp)),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {

            val firstname = username.split(" ").get(0)

            Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "$roomNr. $roomType",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = if (roomState=="Limpo") "$roomState / $firstname" else roomState,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp
                        )
                    )

                    TextButton(onClick = { onCleanSelected() }) {
                        Text(text =
                            if (roomState == "Sujo") "Marcar Como Limpo" else "Marcar Como Sujo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp
                            )
                        )
                    }
            }
            Spacer(modifier = Modifier.height(4.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (observations.isNotEmpty()){
                        Text(text = "${observations.count()} Observações")
                     }
                }
        }
    }
}

@Composable
fun DropDown(
    expandDropDown: Boolean,
    onDismiss: () -> Unit,
    onCleanSelected: () -> Unit,
    onDirtySelected: () -> Unit
) {

    var expanded by remember { mutableStateOf(expandDropDown) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() }
        ) {
            DropdownMenuItem(
                text = { Text("Marcar como limpo") },
                onClick = { onCleanSelected() }
            )
            DropdownMenuItem(
                text = { Text("Marcar como sujo") },
                onClick = { onDirtySelected() }
            )
        }
    }
}