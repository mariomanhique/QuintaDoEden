package com.mariomanhique.quintadoeden.model

import com.mariomanhique.quintadoeden.util.toDate
import java.time.LocalDate
import java.util.Date
import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val adults: Int = 0,
    val eventTitle: String ="",
    val eventType: String = "",
    val zeroTo3Kids: Int = 0,
    val fourTo9Kids: Int = 0, // Confirm this
    val startAt: String = "",
    val endAt: String = "",
    val date: Date = LocalDate.now().toDate(),
    val notes: List<String> = emptyList()
)
// Have a plus button that allows to dynamically
// add stuff like special requests
