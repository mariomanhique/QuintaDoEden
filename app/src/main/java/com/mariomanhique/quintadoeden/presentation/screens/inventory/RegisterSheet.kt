package com.mariomanhique.quintadoeden.presentation.screens.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.components.InputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterMenuSheet(
    onDismissRequest: () -> Unit,
    fillInventoryViewModel: FillInventoryViewModel = hiltViewModel()
) {

    var productState by remember {
        mutableStateOf("")
    }

    var countTypeState by remember {
        mutableStateOf("")
    }

    var countNrState by remember {
        mutableStateOf("")
    }

    ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
        onDismissRequest = onDismissRequest
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                modifier = Modifier
                    .fillMaxWidth(0.9F),

                value = productState,
                placeholder = R.string.product,
                isSingleLine = true,
                maxLines = 1,
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
            ) {
                productState = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            InputField(
                modifier = Modifier
                    .fillMaxWidth(0.9F),
                value = countTypeState,
                placeholder = R.string.countType,
                isSingleLine = true,
                maxLines = 1,
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
            ) {
                countTypeState = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            InputField(
                modifier = Modifier
                    .fillMaxWidth(0.9F),
                value = countNrState,
                placeholder = R.string.countNr,
                isSingleLine = true,
                maxLines = 1,
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
            ) {
                countNrState = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                  if (productState.isNotEmpty() && countNrState.isNotEmpty() && countTypeState.isNotEmpty()){

                     try {
                         fillInventoryViewModel.saveInventory(
                             document = "products",
                             productInv =  ProductInvToSave(
                                 item = productState,
                                 countNr = countNrState,
                                 countType = countTypeState,
                             )
                         )
                     }catch (e: Exception){
                         e.message
                     }
                  }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8F),
                shape = MaterialTheme.shapes.small
                ) {
                Text(text = "Submeter")
            }
        }
    }
}