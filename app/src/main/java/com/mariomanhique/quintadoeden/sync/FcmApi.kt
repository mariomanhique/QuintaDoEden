package com.mariomanhique.quintadoeden.sync

import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST("/send")
   suspend fun sendMessage(
        @Body body: SendMessageDto
   )
    @POST("/send_notification")
    suspend fun sendNotification(
        @Body body: NotificationBody
    )

    @POST("/broadcast")
    suspend fun broadcast(
        @Body body: SendMessageDto
    )
}