package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

data class Guest(
    val guestId: String = "",
    val guestName: String = "",
    val checkInDate: Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
    val checkOutDate: Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
    val adults: Int = 0,
    val child: Int = 0,
    val nights: Int = 0,
    val responsibleCompany: String,
    val roomNr: Room = roomsList.first(),
    val isGuestResident: Boolean = false,
    val amount: Double,
    val reservationStatus: String

)