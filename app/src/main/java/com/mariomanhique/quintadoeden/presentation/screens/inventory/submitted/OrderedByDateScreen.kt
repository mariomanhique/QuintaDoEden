package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.presentation.components.DateHeader
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.InvSection
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderedByDateScreen(
    submittedInventoryViewModel: SubmittedInventoryViewModel = hiltViewModel(),
    paddingValues: PaddingValues = PaddingValues(10.dp)
) {
//    OrderedByDateContent(),

    val dates by submittedInventoryViewModel.items.collectAsState()



    Column(
        modifier = Modifier
//                .padding(horizontal = 24.dp)
            .navigationBarsPadding()
            .padding(top = paddingValues.calculateTopPadding())
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        dates.forEach { (localDate, invProducts) ->
            DateHeader(
                localDate = localDate,
                invProducts = invProducts,
                onHeaderClicked = {
                    Log.d("LocalDate", "OrderedByDateScreen: $localDate")
                }
                )
        }
    }
}

@Composable
fun OrderedByDateContent(
    inventoryDate: LocalDate
) {

    ByDateCard(inventoryDate = inventoryDate)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ByDateCard(
    inventoryDate: LocalDate
) {
    Surface(onClick = { /*TODO*/ }) {
        Text(text = "${inventoryDate}")
    }
}