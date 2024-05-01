package com.mariomanhique.quintadoeden.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mariomanhique.quintadoeden.R

@Composable
fun GuestCard() {
    Surface(
        modifier = Modifier
            .padding(6.dp),
        tonalElevation = 3.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.padding(16.dp)

        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun GuestDetails() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Composable
fun DetailsSection(
    @DrawableRes icon: Int,
    nightsCount: Int? = null,
    adultsCount: Int? = null,
    childCount: Int? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "")
        Spacer(modifier = Modifier.width(10.dp))

        if (nightsCount != null){
            Text(text = "$nightsCount Nights")
        } else if (adultsCount != null){
            if (childCount != null){
                Text(text = "$adultsCount Adult - $childCount Child")
            }
        }
    }
}

@Composable
fun StayingInterval(
    isGuestResident: Boolean
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = "Room 201")
    }

}