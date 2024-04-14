package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted

import androidx.compose.runtime.Composable
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.CategoriesGridList

@Composable
fun SubmittedInventoryScreen(
    categoryClicked: (String) -> Unit,
) {
    CategoriesGridList(
        categoryClicked = categoryClicked
    )
}


@Composable
fun SubmittedInventoryContent() {

}