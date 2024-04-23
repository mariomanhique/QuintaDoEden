package com.mariomanhique.quintadoeden.presentation.screens.inventory.invList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.InvSection
import com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted.SubmittedInventoryViewModel
import java.time.LocalDate

@Composable
fun InvListByDateScreen(
    invByDateViewModel: InvByDateViewModel = hiltViewModel(),
    ) {

    val data by invByDateViewModel.items.collectAsState()

    InvListByDateContent(
        onSaveClicked = {},
        invProductsMap = data
    )

}

@Composable
fun InvListByDateContent(
    onSaveClicked: (ProductInvToSave) -> Unit,
    invProductsMap: List<ProductInvToSave>,
) {
        LazyColumn(
//            modifier = Modifier.padding(paddingValues)
        ) {
            items(items = invProductsMap){
                InvSection(
                    item = it.item,
                    count = it.count,
                    countType = it.countType,
                    countNr = it.countNr
                )
            }
    }


}