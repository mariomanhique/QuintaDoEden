package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.presentation.components.DateHeader
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderedByDateScreen(
    submittedInventoryViewModel: SubmittedInventoryViewModel = hiltViewModel(),
    paddingValues: PaddingValues = PaddingValues(10.dp),
    navigateToOrderedList: (String) -> Unit
) {
    val dates by submittedInventoryViewModel.items.collectAsState()
    val category = submittedInventoryViewModel.category

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
            .padding(top = paddingValues.calculateTopPadding())
            .padding(bottom = paddingValues.calculateBottomPadding())
            .padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
            .padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr))
    ) {
        dates.forEach { (localDate, _) ->
            stickyHeader(
                key = localDate
            ) {
                DateHeader(
                    localDate = localDate,
                    onDateClicked = {
                        navigateToOrderedList("$localDate&$category")
                    }
                )
            }

        }
    }
}

//@Composable
//fun OrderedByDateContent(
//    inventoryDate: LocalDate
//) {
//    ByDateCard(inventoryDate = inventoryDate)
//}
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun ByDateCard(
//    inventoryDate: LocalDate
//) {
//    Surface(onClick = { /*TODO*/ }) {
//        Text(text = "${inventoryDate}")
//    }
//}