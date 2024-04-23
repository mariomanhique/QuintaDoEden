package com.mariomanhique.quintadoeden.presentation.screens.rooms

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.ROOMSTATE
import com.mariomanhique.quintadoeden.model.ROOMTYPE
import com.mariomanhique.quintadoeden.model.Room
import com.mariomanhique.quintadoeden.model.roomsList
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.CheckInContent
import com.mariomanhique.quintadoeden.presentation.screens.checkIn.TabItem


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoomsScreen(
    popBackStack: () -> Unit,
    onRoomSelected: ()-> Unit,
    roomsViewModel: RoomsViewModel = hiltViewModel()
) {

    val cleanedRooms by roomsViewModel.items.collectAsStateWithLifecycle()
    val unCleanedRooms by roomsViewModel.unCleanedRooms.collectAsStateWithLifecycle()

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
                        Text(text = tabItem.title)
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
                        rooms = cleanedRooms
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

    //Filtrar automaticamente quartos sujos
    LazyColumn(
        modifier = Modifier,
//            .navigationBarsPadding()
        verticalArrangement = Arrangement.Top
    ) {
        items(items = rooms){
            RoomCard(
                roomType = it.roomType,
                roomNr = it.roomNr,
                roomState = it.roomState,
                roomAvailability = it.roomAvailability,
                onCleanSelected = { onCleanSelected(it.copy(
                    roomState = "Limpo"
                )) },
                onDirtySelected = { onDirtySelected(
                    it.copy(
                        roomState = "Sujo"
                    )
                ) },
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
            .padding(horizontal = 16.dp)
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small.copy(CornerSize(4.dp)),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$roomNr. $roomType")
                Row {
                    Text(text = roomAvailability)
                    Icon(
                        modifier = Modifier.clickable {
                             dropDownState = true
                        },
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = roomState)
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

    val context = LocalContext.current
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