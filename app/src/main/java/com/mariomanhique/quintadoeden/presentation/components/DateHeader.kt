package com.mariomanhique.quintadoeden.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.presentation.screens.inventory.drinks.InvSection
import java.time.LocalDate


//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun DateHeader(
//    localDate: LocalDate,
//){
//    Surface(
//        onClick = onDateClicked,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(MaterialTheme.colorScheme.surface)
//                    .padding(horizontal = 14.dp)
//                    .padding(vertical = 14.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ){
//
//
//                Column(horizontalAlignment = Alignment.Start,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = String.format("%02d", localDate.dayOfMonth),
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.SemiBold
//                        ),
//                    )
//
//                    Text(
//                        text = localDate.dayOfWeek.toString().take(3),
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontSize = 17.sp,
//                            fontWeight = FontWeight.SemiBold
//                        ),
//                    )
//                }
//                Spacer(modifier = Modifier.width(14.dp))
//                Column(horizontalAlignment = Alignment.Start,
//                    verticalArrangement = Arrangement.Center
//                    ) {
//
//                    Text(
//                        text = localDate.month.toString()
//                            .lowercase()
//                            .replaceFirstChar {
//                                it.titlecase()
//                            },
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontSize = 17.sp,
//                            fontWeight = FontWeight.SemiBold
//                        ),
//
//                    )
//
//                    Text(
//                        text = localDate.year.toString(),
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontSize = 17.sp,
//                            fontWeight = FontWeight.SemiBold
//                        ),
//                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
//                    )
//                }
//            }
//
//        }
//    }
//}

@Composable
fun DateHeader(
    localDate: LocalDate,
    onDateClicked: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onDateClicked()
            }
            .background(MaterialTheme.colorScheme.surface)
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
        }
    }
}