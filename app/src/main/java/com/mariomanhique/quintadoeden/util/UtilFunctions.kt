package com.mariomanhique.quintadoeden.util

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.ZoneId

import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun HandleBackButtonPress(
    onBackPressed: () -> Unit
) {
    BackHandler(enabled = true) {
        onBackPressed.invoke()
    }

}

fun formatSecondsToTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return "%02d:%02d:%02d".format(hours, minutes, remainingSeconds)
}

fun dateParser(date: String):LocalDate{

    val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")

   return LocalDate.parse(date, formatter)
}

fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.emptyLocalDate(): LocalDate{
   return LocalDate.now()
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}