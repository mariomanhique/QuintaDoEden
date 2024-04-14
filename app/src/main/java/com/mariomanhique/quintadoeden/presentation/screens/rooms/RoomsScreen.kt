package com.mariomanhique.quintadoeden.presentation.screens.rooms

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.ROOMSTATE
import com.mariomanhique.quintadoeden.model.ROOMTYPE
import com.mariomanhique.quintadoeden.presentation.components.TopBar


@Composable
fun RoomsScreen(
    popBackStack: () -> Unit,
    onRoomSelected: ()-> Unit
) {
   Column {
       TopBar(
           title = stringResource(id = R.string.cleaning),
           navIcon = Icons.Filled.ArrowBack,
           popBackStack = popBackStack
       )
       RoomsContent(
           onRoomSelected = onRoomSelected
       )

   }
}


@Composable
fun RoomsContent(
    onRoomSelected: ()-> Unit
) {

    //Filtrar automaticamente quartos sujos
    LazyColumn {
        items(count = 10){
            RoomCard(
                roomType = ROOMTYPE.DELUXE_VARANDA.displayName,
                roomNr = 201,
                roomState = ROOMSTATE.CLEAN.displayName,
                isRoomAvailable = true,
                onRoomSelected = onRoomSelected
            )
        }
    }

}


@Composable
fun RoomCard(
    roomType: String,
    roomNr: Int,
    roomState: String,
    isRoomAvailable: Boolean,
    onRoomSelected: ()-> Unit
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
            onCleanSelected = {},
            onDirtySelected = {}
        )

    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.small,
        tonalElevation = 3.dp,
        shadowElevation = 3.dp,
        onClick = onRoomSelected
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$roomNr. $roomType")
                Row {
                    Text(text = if (isRoomAvailable) "Livre" else "Ocupado"?:"Manutenção")
                    Icon(
                        modifier = Modifier.clickable {
                             dropDownState = true
                        },
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = roomState)
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
//        IconButton(onClick = { expanded = !expanded }) {
//            Icon(
//                imageVector = Icons.Default.MoreVert,
//                contentDescription = "More"
//            )
//        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() }
        ) {
            DropdownMenuItem(
                text = { Text("Marcar como limpo") },
                onClick = {
                    Toast.makeText(context, "Load", Toast.LENGTH_SHORT).show()
                }
            )
            DropdownMenuItem(
                text = { Text("Marcar como sujo") },
                onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show() }
            )
        }
    }
}