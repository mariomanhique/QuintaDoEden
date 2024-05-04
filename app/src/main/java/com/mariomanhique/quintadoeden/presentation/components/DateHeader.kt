package com.mariomanhique.quintadoeden.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate


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