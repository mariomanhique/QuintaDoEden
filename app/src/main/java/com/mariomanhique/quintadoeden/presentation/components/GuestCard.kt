package com.mariomanhique.quintadoeden.presentation.components

import androidx.annotation.DrawableRes
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
            StayingInterval(isGuestResident = true)
            Spacer(modifier = Modifier.height(10.dp))
            GuestDetails()
        }
    }
}

@Composable
fun GuestDetails() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Mr. Alex Martin")
            Text(
                text = "€ 100/Noite",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailsSection(
                icon = R.drawable.moon,
                nightsCount = 2
            )

            DetailsSection(
                icon = R.drawable.people,
                adultsCount = 2,
                childCount = 0
            )
        }
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
        Icon(
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
        Card(
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                modifier = Modifier.padding(6.dp),
                text = "12 Mar 24 - 14 Mar 24"
            )
        }
        if (isGuestResident){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .padding(3.dp),
                    color = Color.Yellow
                ){

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "R",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Black
                            )
                        )
                    }
                }
            }
        }
        Text(text = "Room 201")
    }

}