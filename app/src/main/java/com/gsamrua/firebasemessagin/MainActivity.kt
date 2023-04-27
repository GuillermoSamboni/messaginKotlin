package com.gsamrua.firebasemessagin
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.gsamrua.firebasemessagin.firebase.MyFirebaseMessagingService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()


        val firebaseMessagingService = MyFirebaseMessagingService()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("testste", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            //val msg = getString(R.string.msg_token_fmt, token)
            Log.d("testste", "token=> $token")
            //Toast.makeText(baseContext, "msg", Toast.LENGTH_SHORT).show()
        })


       /* FirebaseMessaging.getInstance().subscribeToTopic("usersActive").addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("testste", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            //suscriue to topic

            Log.d("testste", "token=> $token")
            Toast.makeText(baseContext, "msg", Toast.LENGTH_SHORT).show()
        })*/


    }


    private fun createNotificationChannel() {
        // Solo se necesita crear el canal en dispositivos con Android 8.0 (API nivel 26) y versiones posteriores
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default"
            val channelName = "Default Channel"
            val channelDescription = "Default notification channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }


}