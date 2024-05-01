package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

data class Note(
    val noteId: String = UUID.randomUUID().toString(),
    val username: String = "",
    val text: String = "",
    val noteDate: Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),

    )
