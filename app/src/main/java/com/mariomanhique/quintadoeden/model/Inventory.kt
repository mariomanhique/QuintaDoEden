package com.mariomanhique.quintadoeden.model

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

data class ProductInv(
    val id: String = UUID.randomUUID().toString(),
    val item: String = "",
    val count: Int = 0,
    val countNr: String = "", //Unid / Emb(6)
    val countType: String = "" //Unid / Emb(6)
)

data class ProductInvToSave(
    val id: String = UUID.randomUUID().toString(),
    val item: String = "",
    val count: Int = 0,
    val date: Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
    val countNr: String = "", //Unid / Emb(6)
    val countType: String = "" //Unid / Emb(6)
)
