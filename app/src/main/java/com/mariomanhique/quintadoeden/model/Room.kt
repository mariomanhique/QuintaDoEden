package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

data class Room(
    val roomState: String = ROOMSTATE.CLEAN.displayName,
    val roomAvailability: String = ROOMAVAILABILITY.FREE.displayName,
    val roomNr: String = "",
    val lastUpdated: Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
    val roomType: String = "",
    val username: String = "",
    val company: String = "",
    val observations: List<String> = emptyList()//I need to filter this by date when I show
)
