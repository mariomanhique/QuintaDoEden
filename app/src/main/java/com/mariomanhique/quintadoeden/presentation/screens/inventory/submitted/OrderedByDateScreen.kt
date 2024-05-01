package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.DateHeader
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrderedByDateScreen(
    submittedInventoryViewModel: SubmittedInventoryViewModel = hiltViewModel(),
    paddingValues: PaddingValues = PaddingValues(10.dp),
    navigateToOrderedList: (String) -> Unit,
    popBackStack: () -> Unit,
) {
    val dates by submittedInventoryViewModel.items.collectAsState()
    val category = submittedInventoryViewModel.category

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.inventory),
            navIcon = Icons.Filled.ArrowBack,
            onActionClicked = {},
            popBackStack = popBackStack
        )

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