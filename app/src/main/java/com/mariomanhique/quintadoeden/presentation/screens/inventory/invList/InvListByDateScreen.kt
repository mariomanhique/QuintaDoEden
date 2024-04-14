package com.mariomanhique.quintadoeden.presentation.screens.inventory.invList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.InvSection

@Composable
fun InvListByDateScreen() {
    InvListByDateContent(
        onSaveClicked = {},
        itemsList = emptyList()
    )
}

@Composable
fun InvListByDateContent(
    onSaveClicked: (ProductInvToSave) -> Unit,
    itemsList: List<ProductInvToSave>,
) {
    LazyColumn() {
        items(items = itemsList){
            InvSection(
                item = it.item,
                count = it.count,
                countType = it.countType,
                countNr = it.countNr
            )
        }
    }
}