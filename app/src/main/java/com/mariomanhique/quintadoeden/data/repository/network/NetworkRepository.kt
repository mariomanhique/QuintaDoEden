package com.mariomanhique.quintadoeden.data.repository.network

import com.mariomanhique.quintadoeden.sync.NotificationBody
import com.mariomanhique.quintadoeden.sync.SendMessageDto
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkRepository {

    suspend fun sendMessage(body: SendMessageDto)
    suspend fun sendNormalNotification(body: NotificationBody)

    suspend fun broadcast(body: SendMessageDto)
}