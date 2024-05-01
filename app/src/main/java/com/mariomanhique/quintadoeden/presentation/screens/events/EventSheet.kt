package com.mariomanhique.quintadoeden.presentation.screens.events

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.util.toDate
import com.mariomanhique.quintadoeden.model.Event
import com.mariomanhique.quintadoeden.presentation.components.EdenIcon
import com.mariomanhique.quintadoeden.presentation.components.InputField
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventSheet(
    onDismissRequest: () -> Unit,
    eventViewModel: EventViewModel = hiltViewModel()
) {

    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf(LocalTime.now()) }
    val dateDialog = rememberSheetState()
    val endTimeDialog = rememberSheetState()
    val startTimeDialog = rememberSheetState()
    var adultsState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var eventTitleState by remember {
        mutableStateOf("")
    }

    var eventType by remember {
        mutableStateOf("")
    }

    var obsState by remember {
        mutableStateOf("")
    }
    var kid0ToState by remember {
        mutableStateOf("")
    }

    var kids4ToState by remember {
        mutableStateOf("")
    }

    val formattedDate = remember(key1 = currentDate) {
        DateTimeFormatter
            .ofPattern("yyyy-MM-d")
            .format(currentDate).uppercase()
    }
    val formattedStartTime = remember(key1 = startTime) {
        DateTimeFormatter
            .ofPattern("hh:mm a")
            .format(startTime).uppercase()
    }

    val formattedEndTime = remember(key1 = endTime) {
        DateTimeFormatter
            .ofPattern("hh:mm a")
            .format(endTime).uppercase()
    }

    val sheetState = rememberSheetState(embedded = false)

    var dateTimeUpdated by remember { mutableStateOf(false) }

//    val selectedDiaryDateTime = remember(selectedDiary) {
//        if (selectedDiary != null) {
//            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a", Locale.getDefault())
//                .withZone(ZoneId.systemDefault())
//                .format(selectedDiary.date.toInstant())
//        } else "Unknown"
//    }

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

//    val scrennEdges = configuration.

    AlertDialog(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            ),
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismissRequest,
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            CalendarDialog(
                state = dateDialog,
                selection = CalendarSelection.Date { localDate ->
                    currentDate = localDate
                },
                config = CalendarConfig(monthSelection = true, yearSelection = true)
            )

            ClockDialog(
                state = startTimeDialog,
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    startTime = LocalTime.of(hours, minutes)
                    dateTimeUpdated = true
                }
            )

            ClockDialog(
                state = endTimeDialog,
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    endTime = LocalTime.of(hours, minutes)
                    dateTimeUpdated = true
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            EdenIcon(
                subtitle = "Eventos"
            )

            InputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = eventTitleState,
                placeholder = R.string.event,
                isSingleLine = true,
                maxLines = 1,
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
            ) {
                eventTitleState = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            InputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = eventType,
                placeholder = R.string.event_type,
                isSingleLine = true,
                maxLines = 1,
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
            ) {
                eventType = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            InputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = formattedDate,
                trailingIcon = {
                    IconButton(onClick = {
                        dateDialog.show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = ""
                        )
                    }
                },
                placeholder = R.string.date,
                isSingleLine = true,
                maxLines = 1,
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
            ) {

            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                InputField(
                    modifier = Modifier
                        .weight(2F),
                    value = formattedStartTime,
                    trailingIcon = {
                        IconButton(onClick = {
                            startTimeDialog.show()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = ""
                            )
                        }
                    },
                    placeholder = R.string.start_at,
                    isSingleLine = true,
                    maxLines = 1,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
                ) {

                }

                InputField(
                    modifier = Modifier
                        .weight(2F),
                    value = formattedEndTime,
                    trailingIcon = {
                        IconButton(onClick = {
                            endTimeDialog.show()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = ""
                            )
                        }
                    },
                    placeholder = R.string.end_at,
                    isSingleLine = true,
                    maxLines = 1,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
                ) {

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                Text(
                    text = "Pax/Idade:",
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    InputField(
                        modifier = Modifier
                            .weight(2F),
                        value = adultsState,
                        placeholder = R.string.adults,
                        isSingleLine = true,
                        maxLines = 1,
                        shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
                    ) {
                        adultsState = it
                    }

                    InputField(
                        modifier = Modifier
                            .weight(2F),
                        value = kid0ToState,

                        placeholder = R.string.kids_zeroTo3,
                        isSingleLine = true,
                        maxLines = 1,
                        shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
                    ) {
                        kid0ToState = it
                    }
                    InputField(
                        modifier = Modifier
                            .weight(2F),
                        value = kids4ToState,
                        placeholder = R.string.kids_4To9,
                        isSingleLine = true,
                        maxLines = 1,
                        shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
                    ) {
                        kids4ToState = it
                    }

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Section(
                value = obsState,
                onValueChange = {
                    obsState = it
                },
                onAddClicked = {

                }) {



            }
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {

                    if(eventTitleState.isNotEmpty() && eventType.isNotEmpty() && adultsState.isNotEmpty() && kid0ToState.isNotEmpty() && kids4ToState.isNotEmpty()){
                        eventViewModel.saveEvent(
                            event = Event(
                                adults = adultsState.toInt(),
                                eventType = eventType,
                                eventTitle = eventTitleState,
                                zeroTo3Kids = kid0ToState.toInt(),
                                fourTo9Kids = kids4ToState.toInt(),
                                startAt = formattedStartTime,
                                endAt = formattedEndTime,
                                date = currentDate.toDate()
                            ),
                            onSuccess = onDismissRequest,
                            onError = {}
                        )
                    }else{
                        Toast.makeText(context,"Preencha devidamente os campos",Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Submeter")
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun Section(
    value: String,
    onValueChange: (String) -> Unit,
    onAddClicked: () -> Unit,
    content: @Composable (ColumnScope.() ->Unit)
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        InputField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.30f),
            value = value,
            trailingIcon = {
                IconButton(onClick = {
                    onAddClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = ""
                    )
                }
            },
            placeholder = R.string.observation,
            onValueChange = onValueChange,
            isSingleLine = false,
            maxLines = Int.MAX_VALUE,
            shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp))
        )

        content()
    }

}