package com.gsamrua.firebasemessagin.firebase

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gsamrua.firebasemessagin.MyApplication
import com.gsamrua.firebasemessagin.NotificationActivity
import com.gsamrua.firebasemessagin.R


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val body = remoteMessage.data


        val application = applicationContext as MyApplication

        if (application.isAppInForeground()) {
            val intent = Intent(applicationContext, NotificationActivity::class.java).apply {
                putExtra("title", title)
                putExtra("body", body.toString())
                putExtra("first", body.toString())
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }

        if (!application.isAppInForeground()) {
            showNotification(this, "Hola", "Mensaje")
        }
        Log.e("Stadoapp", "" + application.isAppInForeground())

    }


    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(context: Context, title: String?, message: String?) {
        // Verificar si el dispositivo tiene Android Oreo (API 26) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Crear el canal de notificación para Android Oreo y versiones posteriores
            val channelId = "channel_id"
            val channelName = "Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        // Crear un intent para abrir la actividad de notificación cuando se hace clic en la notificación
        val notificationIntent = Intent(context, NotificationActivity::class.java).apply {

            putExtra("title", title.toString())
            putExtra("body", message.toString())
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Aquí puedes agregar datos adicionales al intent si es necesario
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        // Construir la notificación
        val notificationBuilder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Establecer el intent al hacer clic en la notificación
            .setAutoCancel(true) // Cerrar la notificación automáticamente al hacer clic en ella

        // Mostrar la notificación
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManagerCompat.notify(1, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}




