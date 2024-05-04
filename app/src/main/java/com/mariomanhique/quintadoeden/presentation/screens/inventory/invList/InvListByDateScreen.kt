package com.mariomanhique.quintadoeden.presentation.screens.inventory.invList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.InvSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvListByDateScreen(
    invByDateViewModel: InvByDateViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    ) {

    val data by invByDateViewModel.items.collectAsState()

    Column {
        TopBar(
            title = stringResource(id = R.string.inventory),
            navIcon = Icons.Filled.ArrowBack,
            onActionClicked = {},
            popBackStack = popBackStack
        )
        InvListByDateContent(
            invProductsMap = data
        )
    }


}

@Composable
fun InvListByDateContent(
    invProductsMap: List<ProductInvToSave>,
) {
        LazyColumn(
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