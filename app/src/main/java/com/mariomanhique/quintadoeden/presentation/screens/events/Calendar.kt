package com.mariomanhique.quintadoeden.presentation.screens.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.mariomanhique.quintadoeden.model.Event
import com.mariomanhique.quintadoeden.util.toLocalDate
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomCalendar(
    events: Map<LocalDate,List<Event>>
) {

    val configuration = LocalConfiguration.current

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
    var calendarDay by remember {
        mutableStateOf(CalendarDay(date = LocalDate.now(), position = DayPosition.MonthDate))
    }

    val scope = rememberCoroutineScope()

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                scope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
                }
            }) {
                Icon(imageVector = Icons.Filled.ArrowBackIos, contentDescription = "")
            }

            Text(text = "${state.firstVisibleMonth.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PT"))
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()}} ${state.firstVisibleMonth.yearMonth.year}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    //color = MaterialTheme.colorScheme.secondary
                )
                )

            IconButton(onClick = {
                scope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
                }
            }) {
                Icon(imageVector = Icons.Filled.ArrowForwardIos, contentDescription = "")
            }
        }

       //DaysOfWeekTitle(daysOfWeek = daysOfWeek)
        HorizontalCalendar(
            state = state,
            dayContent = {
                Day(
                    day = it,
                    events = events,
                    onClick = {
                    calendarDay = it
            },
            currentDate = {currentCalendarDate->
//                calendarDay = currentCalendarDate
            }) },
            monthHeader = {month->
                val daysOfWeek = remember {
                    month.weekDays.first().map { it.date.dayOfWeek }
                }
                DaysOfWeekTitle(daysOfWeek = daysOfWeek) // Use the title as month header
            }
        )

        Spacer(modifier = Modifier.height(2.dp))
        if (events.isNotEmpty()){
            LazyColumn() {
                events.forEach { localDate, events ->
                   items(items =  events.filter {
                       it.date.toLocalDate() == calendarDay.date
                   }){
                       Row(
                           modifier = Modifier.fillMaxWidth()
                       ) {
                           EventMiniCard(
                               localDate = it.date.toLocalDate()
                           )
                           EventMiniCard(
                               Modifier.width((configuration.screenWidthDp.dp/7)*6),
                               eventTitle = it.eventTitle
                           )
                       }
                   }

                    if (events.filter {
                            it.date.toLocalDate() == calendarDay.date
                        }.isEmpty()){
                        item {
                            Text(
                                text = "Nenhum Evento Marcado para o dia ${calendarDay.date.dayOfMonth} de" +
                                        " ${calendarDay.date.month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PT"))
                                            .replaceFirstChar {
                                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                            }} de ${calendarDay.date.year}",
                                maxLines = 4,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }

                }
            }

        }

       // if (calendarDay.date)
    }


//    If you need a vertical calendar.
//    VerticalCalendar(
//        state = state,
//        dayContent = { Day(it) }
//    )

//
//    val state = rememberWeekCalendarState(
//        startDate = startDate,
//        endDate = endDate,
//        firstVisibleWeekDate = currentDate,
//        firstDayOfWeek = firstDayOfWeek
//    )
//    WeekCalendar(
//        state = state,
//        dayContent = { Day(it) }
//    )
}


@Composable
fun EventMiniCard(
    modifier: Modifier = Modifier,
    localDate: LocalDate? = null,
    eventTitle: String = ""
) {

    val configuration = LocalConfiguration.current
    val squareDp = configuration.screenWidthDp.dp / 7

    Surface(
        shape = Shapes().small.copy(CornerSize(0.dp)),
        modifier = modifier
            .width(squareDp * 2)
            .height(squareDp)
            .padding(1.dp),
            color = (if (localDate != null){
                Color(0XFFEcdd7b)
            }else{
                Color.Transparent
            }),
        tonalElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(text = if (localDate != null)
                "${localDate.dayOfMonth} ${localDate.month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PT")).uppercase()}"
            else
                eventTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = if (localDate != null) Color.Black else MaterialTheme.typography.titleMedium.color
                )
            )
        }
    }
}
@Composable
fun Day(
    day: CalendarDay,
    events: Map<LocalDate,List<Event>>,
    onClick: (CalendarDay) -> Unit,
    currentDate: (CalendarDay) -> Unit,
) {
    var color by remember {
        mutableStateOf(Color.Transparent)
    }
    Surface(
        onClick = { /*TODO*/ },
        tonalElevation = 3.dp,
       // shadowElevation = 3.dp,
        modifier = Modifier.padding(1.dp),
        border = if (day.date == LocalDate.now()) BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline) else null,
        ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f) // This is important for square sizing!
                .clickable(
                    enabled = day.position == DayPosition.MonthDate,
                    onClick = { onClick(day) }
                ),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) MaterialTheme.typography.titleMedium.color else Color.Gray,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                modifier = Modifier.padding(top = 4.dp, end = 4.dp)
            )

            events.map {mappedEvents->
                if (day.date == mappedEvents.key){
                    currentDate(day)
                   val filteredEvents = mappedEvents.value.filter {
                        it.date.toLocalDate() == mappedEvents.key
                    }
                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .wrapContentSize()
                            //.background(Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            filteredEvents.forEach {
                                Surface(
                                    modifier = Modifier
                                        .height(6.dp)
                                        .fillMaxWidth(),
                                    color = if (it.eventType.lowercase() == "casamento") Color(0XFFc9aa12) else Color(0XFF2B79FE)
                                ) {}
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun EventMarker() {
    
}
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("PT")).take(3).uppercase(),
            )
        }
    }
}