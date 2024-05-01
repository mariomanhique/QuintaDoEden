package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted

import androidx.compose.runtime.Composable
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.CategoriesGridList

@Composable
fun SubmittedCategoriesScreen(
    categoryClicked: (String) -> Unit,
//    popBackStack: () -> Unit,
) {
    CategoriesGridList(
        categoryClicked = categoryClicked
    )
}