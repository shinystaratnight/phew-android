package com.app.phew.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.app.phew.BuildConfig
import com.app.phew.R
import com.app.phew.preferences.SharedPrefManager
import com.app.phew.utils.NotificationID

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "10001"
    }

    private var notificationBody = ""
    private var notificationType = ""
    private var senderId = -1
    private var senderPhone = ""
    private var notificationTitle = ""
    private var orderId = -1


    private lateinit var mIntent: Intent

    private lateinit var mSharedPrefManager: SharedPrefManager

    private var mNotificationManager: NotificationManager? = null

    private var mBuilder: NotificationCompat.Builder? = null

    private var notificationId: Int = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.e("NOTIFICATION_DATA", "NotificationData: " + Gson().toJson(remoteMessage.data))


        }
    }

    private fun createNotification(title: String, message: String, resultIntent: Intent?) {

        resultIntent!!.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)

        val resultPendingIntent = PendingIntent.getActivity(
            application, 0, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        mBuilder = NotificationCompat.Builder(application, NOTIFICATION_CHANNEL_ID)
        mBuilder!!.setSmallIcon(R.drawable.app_icon_round)
        mBuilder!!.setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setContentIntent(resultPendingIntent)

        mNotificationManager =
            application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            if (BuildConfig.DEBUG && mNotificationManager == null) error("Assertion failed")
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        if (BuildConfig.DEBUG && mNotificationManager == null) error("Assertion failed")
        notificationId = NotificationID.id
        mNotificationManager!!.notify(notificationId, mBuilder!!.build())
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        mSharedPrefManager.authToken = newToken
    }
}