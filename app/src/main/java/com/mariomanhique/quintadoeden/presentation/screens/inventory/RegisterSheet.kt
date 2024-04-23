package com.mariomanhique.quintadoeden.presentation.screens.inventory

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import kotlinx.coroutines.launch

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
                maxLines = 1
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
                maxLines = 1
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
                maxLines = 1
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

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    maxLines: Int,
    isSingleLine: Boolean,
    scrollState: ScrollState?=null,
    value: String,
    trailingIcon:  @Composable() (() -> Unit)? = null,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit
) {

    val scope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }


    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .focusRequester(focusRequester = focusRequester),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
            Text(text = stringResource(id = placeholder))
        },
//        keyboardActions = KeyboardActions(
//            onNext = {
//                if(focusDirection != null){
//                    focusManager.moveFocus(focusDirection)
//                }else{
//                    scope.launch {
//                        scrollState?.animateScrollTo(Int.MAX_VALUE)
//                    }
//                    focusManager.clearFocus()
//                }
//            }
//        ),
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        singleLine = isSingleLine
    )
}