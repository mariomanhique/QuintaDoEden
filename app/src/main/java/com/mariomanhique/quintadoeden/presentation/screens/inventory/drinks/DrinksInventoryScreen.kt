package com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.Category
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.screens.inventory.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinksInventoryScreen(
    popBackStack: () -> Unit,
    onAddInventoryClicked: () -> Unit,
    categoryClicked: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(id = R.string.inventory),
            navIcon = Icons.Filled.ArrowBack,
            onActionClicked = onAddInventoryClicked,
            popBackStack = popBackStack
        )

        CategoriesGridList(
            categoryClicked = categoryClicked
        )
    }
}

@Composable
fun CategoriesGridList(
    categoryClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 6.dp),
        columns = object : GridCells{
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                val firstColumn = (availableSize - spacing) * 2 / 4 // this gives us the value of the second grid times 2
                val secondColumn = availableSize - spacing - firstColumn

                return listOf(firstColumn, secondColumn)
            }
        },
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(
            items = listOf(Category(
                title = "Bebidas",
                icon = R.drawable.whiskey),
                Category(
                    title = "Pastelaria",
                    icon = R.drawable.croissant2),
                Category(
                    title = "Limpeza",
                    icon = R.drawable.cleaning),Category(
                    title = "Elis",
                    icon = R.drawable.cleanclothes
                ))
        ){
            InventoryCard(
                category = it.title,
                icon = it.icon,
                categoryClicked = { categoryClicked(it.title.lowercase()) }
            )
        }
    }
}

@Composable
fun InventoryCard(
    category: String,
    @DrawableRes icon: Int,
    categoryClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .height(150.dp),
        onClick = {
            categoryClicked()
        },
        tonalElevation = 3.dp,
        shadowElevation = 3.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ){
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = icon),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = category,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}