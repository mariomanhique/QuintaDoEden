package com.mariomanhique.quintadoeden.sync

data class SendMessageDto(
    val to: String?=null,
    val notificationBody: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)