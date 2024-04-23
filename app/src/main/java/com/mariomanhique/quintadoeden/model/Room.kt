package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

data class Room(
//    val id: String = UUID.randomUUID().toString(),
    val roomState: String = ROOMSTATE.CLEAN.displayName,
    val roomAvailability: String = ROOMAVAILABILITY.FREE.displayName,
    val roomNr: String = "",
    val lastUpdated: Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
    val roomType: String = "",
    val observations: List<String> = emptyList()//I need to filter this by date when I show
)

val roomsList = listOf(
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.displayName,
        roomNr = "201",
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.displayName,
        roomNr = "201",
        roomState = ROOMSTATE.CLEAN.displayName
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = "201",
        roomState = ROOMSTATE.CLEAN.name
    ),
)
