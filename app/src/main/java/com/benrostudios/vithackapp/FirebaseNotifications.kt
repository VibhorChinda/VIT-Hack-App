package com.benrostudios.vithackapp


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.benrostudios.vithackapp.ui.splash.SplashActivity
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseNotifications : FirebaseMessagingService() {
    private var mNotificationManager: NotificationManager? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var title: String = ""
        var body: String = ""
        remoteMessage.notification?.let {
            title = it.title.toString()
            body = it.body.toString()
        }
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "0")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setPriority(Notification.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        createNotificationChannel()
        mNotificationManager!!.notify(Math.random().toInt(), builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.notification_channel_name)
            val description: String = getString(R.string.notification_channel_disp)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("0", name, importance)
            channel.description = description
            channel.enableLights(true)
            channel.enableVibration(true)
            mNotificationManager = getSystemService(NotificationManager::class.java)
            mNotificationManager!!.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        val uid = FirebaseAuth.getInstance().uid
        Firebase.database.getReference("/users/$uid").child("token").setValue(token)
            .addOnFailureListener {
                Log.d("tokenUpdate", "$it")
            }
    }

    companion object {
        const val TAG = "FirebaseNotifications"
    }
}