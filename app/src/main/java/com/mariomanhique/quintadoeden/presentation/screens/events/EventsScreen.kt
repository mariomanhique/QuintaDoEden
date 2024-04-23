package com.mariomanhique.quintadoeden.presentation.screens.events

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.model.Event
import com.mariomanhique.quintadoeden.presentation.components.DateHeader
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import com.mariomanhique.quintadoeden.util.Elevation
import com.mariomanhique.quintadoeden.util.toLocalDate
import java.time.LocalDate

@Composable
fun EventsScreen(
    eventViewModel: EventViewModel = hiltViewModel()
) {

    val events by eventViewModel.items.collectAsStateWithLifecycle()

    EventsContent(events)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsContent(
    events: Map<LocalDate, List<Event>>
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    var dialogState by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(visible = dialogState) {
        EventSheet(
            onDismissRequest = {
                dialogState = false
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.events),
                scrollBehavior = scrollBehavior,
                actionIcon = Icons.Filled.Add,
                onActionClicked = {
                    dialogState = true
                },
                popBackStack = {}
            )
        }
    ) {
        EventsLazyList(
            paddingValues = it,
            events = events
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventsLazyList(
    paddingValues: PaddingValues,
    events: Map<LocalDate, List<Event>>
) {
    if (events.isNotEmpty()){
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
                .padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)),
//            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            events.forEach { (localDate, events) ->
                stickyHeader {
                    DateHeader(localDate =localDate, onDateClicked = {})
                }

                items(items = events){
                    EventCard(
                        modifier = Modifier.animateItemPlacement(
                            tween(durationMillis = 250)
                        ),
                        date = it.date.toLocalDate(),
                        zeroTo3Kids = it.zeroTo3Kids,
                        adultsCount = it.adults,
                        fourTo9Kids = it.fourTo9Kids
                    )
                }
            }


        }
    }

}

@Composable
fun EventCard(
    modifier: Modifier,
    date: LocalDate? = null,
    event: String = "Casamento Fulano e Fulano",
    zeroTo3Kids: Int,
    adultsCount: Int,
    fourTo9Kids: Int
) {

    val localDensity = LocalDensity.current

    Row {
        var componentHeight by remember { mutableStateOf(0.dp) }

        Surface(
            modifier = Modifier
                .width(3.dp)
                .height(componentHeight + 14.dp),
            tonalElevation = Elevation.Level2) {}

        Spacer(modifier = Modifier.width(20.dp))

        Surface(
            modifier = Modifier
                .clip(
                    shape = Shapes().medium.copy(all = CornerSize(8.dp))
                ),
            tonalElevation = Elevation.Level1
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        componentHeight = with(localDensity) { it.size.height.toDp() }
                    }
        //            .padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        shape = MaterialTheme.shapes.small.copy(CornerSize(4.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.Cyan)
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = "$date"
                        )
                    }

                    Text(text = "Total Pax: ${adultsCount + zeroTo3Kids + fourTo9Kids}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(end = 6.dp)
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))
                DetailsSection(icon = R.drawable.people, event = event,zeroTo3Kids = zeroTo3Kids, adultsCount = adultsCount, fourTo9Kids = fourTo9Kids)
            }
        }

    }

}

@Composable
fun DetailsSection(
    @DrawableRes icon: Int,
    event: String,
    zeroTo3Kids: Int,
    adultsCount: Int,
    fourTo9Kids: Int
) {

    Column(
        modifier = Modifier.padding(6.dp),
    ) {
        Text(text = event,
            modifier = Modifier.padding(vertical = 6.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = " Ad: ${adultsCount}  | Cr (0 - 3): ${zeroTo3Kids} | Cr (4 - 9): ${fourTo9Kids}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
            )
        }
    }

}