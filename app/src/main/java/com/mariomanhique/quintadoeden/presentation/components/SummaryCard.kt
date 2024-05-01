package com.mariomanhique.quintadoeden.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    count: Int,
    onSummaryClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .clickable{
                    onSummaryClicked()
                }.size(90.dp)
                .padding(3.dp)
                ,
            shape = CircleShape,
            shadowElevation = 3.dp,
            tonalElevation = 3.dp,
            color = MaterialTheme.colorScheme.onSecondary
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "$count",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 25.sp
                    )
                )
            }

        }
        Text(text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium.copy(
//                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        )
    }
}