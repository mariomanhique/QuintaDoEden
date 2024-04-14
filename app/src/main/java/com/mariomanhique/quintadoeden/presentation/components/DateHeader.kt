package com.mariomanhique.quintadoeden.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.InvSection
import java.time.LocalDate

@Composable
fun DateHeader(
    localDate: LocalDate,
    invProducts: List<ProductInvToSave>,
    onHeaderClicked: () -> Unit
){

    var itemsVisibility by remember {
        mutableStateOf(false)
    }
    Surface(
        onClick = {
            itemsVisibility = !itemsVisibility
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 14.dp)
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ){


                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = String.format("%02d", localDate.dayOfMonth),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
    //                fontFamily = fontFamily(
    //                    fontName = "Chakra Petch",
    //                    fontWeight = FontWeight.Light,
    //                    fontStyle = FontStyle.Normal
    //                )
                    )

                    Text(
                        text = localDate.dayOfWeek.toString().take(3),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
    //                fontFamily = fontFamily(
    //                    fontName = "Chakra Petch",
    //                    fontWeight = FontWeight.Light,
    //                    fontStyle = FontStyle.Normal
    //                )
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(horizontalAlignment = Alignment.Start) {

                    Text(
                        text = localDate.month.toString()
                            .lowercase()
                            .replaceFirstChar {
                                it.titlecase()
                            },
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
    //                fontFamily = fontFamily(
    //                    fontName = "Chakra Petch",
    //                    fontWeight = FontWeight.Light,
    //                    fontStyle = FontStyle.Normal
    //                )
                    )

                    Text(
                        text = localDate.year.toString(),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
    //                fontFamily = fontFamily(
    //                    fontName = "Chakra Petch",
    //                    fontWeight = FontWeight.Light,
    //                    fontStyle = FontStyle.Normal
    //                )
                    )
    //            localDate.atStartOfDay()

                }
            }



            AnimatedVisibility(visible = itemsVisibility) {
                LazyColumn {
                    items(items = invProducts){
                        InvSection(
                            item = it.item,
                            count = it.count,
                            onSaveClicked = null,
                            countType = it.countType,
                            countNr = it.countNr
                        )
                    }
                }
            }
        }
    }
}