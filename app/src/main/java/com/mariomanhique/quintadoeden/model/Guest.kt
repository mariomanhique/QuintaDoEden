package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

data class Guest(
    val guestId: String = "",
//    val guestName: String = "",
    val checkInDate: Date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()),
    val checkOutDate: Date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()),
//    val adults: Int = 0,
//    val child: Int = 0,
//    val nights: Int = 0,
//    val responsibleCompany: String,
    val isCheckIn: Boolean = false,
    val room: Room = Room(),
    val observations: List<String> = emptyList()//I need to filter this by date when I show
//    val isGuestResident: Boolean = false,
//    val amount: Double,
//    val reservationStatus: String

)

val guestList = listOf(Guest(
    isCheckIn = true,
    room =  Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.displayName,
        roomNr = "201",
        roomState = ROOMSTATE.CLEAN.name
    )
))