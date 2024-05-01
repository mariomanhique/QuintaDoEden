package com.mariomanhique.quintadoeden.data.repository.network

import com.mariomanhique.quintadoeden.sync.FcmApi
import com.mariomanhique.quintadoeden.sync.NotificationBody
import com.mariomanhique.quintadoeden.sync.SendMessageDto
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val fcmApi: FcmApi
): NetworkRepository {
    override suspend fun sendMessage(body: SendMessageDto) {
       fcmApi.sendMessage(body)
    }

    override suspend fun sendNormalNotification(body: NotificationBody) {
        fcmApi.sendNotification(body)
    }

    override suspend fun broadcast(body: SendMessageDto) {
        fcmApi.broadcast(body)
    }


}