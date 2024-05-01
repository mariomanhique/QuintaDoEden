package com.mariomanhique.quintadoeden

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mariomanhique.quintadoeden.R
class PushNotificationService(): FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("New Message", "onMessageReceived:${message.notification?.body} ")
        val channelId = "EdenNotifications"
        val channelName = "Quinta Do Eden"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance)
        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)

        val notificationTitle = message.notification?.title ?: getString(R.string.default_notification_title)
        val notificationBody = message.notification?.body ?: getString(R.string.default_notification_body)
        val user  = FirebaseAuth.getInstance().currentUser?.displayName

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(
                if (notificationTitle == "Nova Menssagem") user else notificationTitle
            )
            .setContentText(notificationBody)
            .setSmallIcon(R.drawable.people)
            .setAutoCancel(true)
            .build()



//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_DENIED) {
//            return
//        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(1, notification)
    }

}