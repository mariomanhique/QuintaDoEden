package com.mariomanhique.quintadoeden.presentation.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.presentation.components.SummaryCard

@Composable
fun HomeScreen(
    navigateToCleaning: () -> Unit,
    navigateToCheckIn: () -> Unit,
    onDrinksInventoryClicked: () -> Unit,
){
    Scaffold(
    ) {
        HomeContent(
            paddingValues = it,
            navigateToCleaning = navigateToCleaning,
            navigateToCheckIn = navigateToCheckIn,
            onDrinksInventoryClicked = onDrinksInventoryClicked
            )
    }
}

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    navigateToCleaning: () -> Unit,
    navigateToCheckIn: () -> Unit,
    onDrinksInventoryClicked: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            //.padding(paddingValues)
            .padding(paddingValues.calculateStartPadding(LayoutDirection.Ltr))
            .padding(paddingValues.calculateStartPadding(LayoutDirection.Rtl)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier =Modifier.height(6.dp))
        Surface(
            modifier = Modifier,
            tonalElevation = 3.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                SummaryCard(
                    title = R.string.check_in,
                    count = 3,
                    onSummaryClicked = navigateToCheckIn
                )
                SummaryCard(
                    title = R.string.cleaning,
                    count = 3,
                    onSummaryClicked = {
                        navigateToCleaning()
                    })
                SummaryCard(
                    title = R.string.checkout,
                    count = 10,
                    onSummaryClicked = {}
                )
                SummaryCard(
                    title = R.string.booking,
                    count = 1,
                    onSummaryClicked = {}
                    )
                SummaryCard(
                    title = R.string.in_house,
                    count = 20,
                    onSummaryClicked = {}
                )
            }


        }
        Spacer(modifier = Modifier.height(10.dp))
            HomeSection(title = R.string.inventory) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Surface(
                        modifier = Modifier
                            .height(150.dp)
                            .weight(1F),
                        onClick = onDrinksInventoryClicked,
                        shape = MaterialTheme.shapes.small,
                        shadowElevation = 4.dp
                    ) {
                        Image(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            painter = painterResource(id = R.drawable.inv),
                            contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Surface(
                        modifier = Modifier
                            .height(150.dp)
                            .weight(3F),
                        onClick = {},
                        shape = MaterialTheme.shapes.small,
                        shadowElevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            SubSection(
                                title = R.string.available,
                                progress = 0.8F,
                                rooms = 15
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            SubSection(
                                title = R.string.sold,
                                progress = 0.8F,
                                rooms = 15
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            SubSection(
                                title = R.string.blocked,
                                progress = 0.8F,
                                rooms = 15
                            )
                        }
                    }
                }

            }

    }
}

@Composable
fun SubSection(
    @StringRes title: Int,
    progress: Float,
    rooms: Int
) {
    Column(
        modifier =  Modifier.fillMaxWidth(),
        horizontalAlignment =Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier =  Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = title))
            Text(text = "$rooms")
        }
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            modifier = Modifier.height(6.dp),
            progress = progress
        )
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    content: @Composable ()  -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = title))
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }

}