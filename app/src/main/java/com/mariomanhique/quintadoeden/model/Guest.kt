package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

data class Guest(
    val checkInDate: Date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()),
    val checkOutDate: Date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()),
    @field:JvmField
    val isCheckIn: Boolean = false,
    @field:JvmField
    val isCheckOut: Boolean = false,
    val roomNr: String = "",
    val observations: List<String> = emptyList()//I need to filter this by date when I show
)
