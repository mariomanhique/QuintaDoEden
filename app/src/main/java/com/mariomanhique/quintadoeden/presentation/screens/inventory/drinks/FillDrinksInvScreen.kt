package com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.NumberPicker
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.screens.inventory.FillInventoryViewModel
import com.mariomanhique.quintadoeden.presentation.screens.inventory.RegisterMenuSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillDrinksInventoryScreen(
    fillInventoryViewModel: FillInventoryViewModel = hiltViewModel()
) {
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val items by fillInventoryViewModel.items.collectAsState()

    var sheetState by remember {
        mutableStateOf(false)
    }

    if (sheetState){
        RegisterMenuSheet(
            onDismissRequest = {
                sheetState = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
      TopAppBar(title = {
            Text(text = stringResource(id = R.string.newDrinkInv))
      }, actions = {
//          TextButton(onClick = {
//          }) {
//              Text(text = "Submeter")
//          }
          val displayName = FirebaseAuth.getInstance().currentUser?.displayName
          if (displayName != null && displayName == "Mario Manhique"){
              IconButton(onClick = {
                  sheetState = true
              }) {
                  Icon(
                      imageVector = Icons.Filled.Add,
                      contentDescription = ""
                  )
              }
          }
      })
        FillDrinksInventoryContent(
            itemsList = items,
            onSaveClicked = {
                fillInventoryViewModel.saveInventory(it)
            }
        )
    }
}

@Composable
fun FillDrinksInventoryContent(
    onSaveClicked: (ProductInvToSave) -> Unit,
    itemsList: List<ProductInv>,
) {
    LazyColumn {
        items(items = itemsList){
            InvSection(
                item = it.item,
                onSaveClicked = {count->
                    onSaveClicked(
                        ProductInvToSave(
                        id = it.id,
                        item = it.item,
                        countNr = it.countNr,
                        count = count,
                        countType = it.countType
                    )
                  )
                },
                countType = it.countType,
                countNr = it.countNr
            )
        }
    }
}

@Composable
fun InvSection(
    item: String,
    countType: String,
    countNr: String,
    count: Int = 0,
    onSaveClicked: ((Int) -> Unit?)? = null
) {

    var dialogState by remember {
        mutableStateOf(false)
    }

    var number by remember {
            mutableIntStateOf(count)
    }

    if (dialogState){
        NumberPickerDialog(
            onDialogDismissed = {
                dialogState = false
            },
            onConfirmClicked = {
                if (onSaveClicked != null) {
                    onSaveClicked(it)
                }
                number = it
                dialogState = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$item ($countType - $countNr)",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp,
                    fontSize = 18.sp
                )
            )

            Surface(onClick = {
                  if (onSaveClicked != null){
                      dialogState = true
                  }
            },
            tonalElevation = 1.dp,
            shadowElevation = 6.dp,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    if (onSaveClicked != null) {
                        dialogState = true
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "$number",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

        }
        Divider(modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp))
    }
}

@Composable
fun NumberPickerDialog(
    onDialogDismissed: () -> Unit,
    onConfirmClicked: (Int) -> Unit
) {
    var number by remember {
        mutableIntStateOf(0)
    }
    AlertDialog(
        onDismissRequest = onDialogDismissed,
        confirmButton = {

            Button(onClick = {
                onConfirmClicked(number)
            },
            shape = MaterialTheme.shapes.small) {
                Text(
                    text = "Salvar",
                    style = MaterialTheme.typography.bodyLarge.copy(
//                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumberPicker(
                    modifier = Modifier.fillMaxWidth(),
                    value = number,
                    onValueChange = {
                        number = it
                    },
                    range = 1..100,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                    )
            }

        }
    )

}
