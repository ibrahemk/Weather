package com.example.weather.Broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

import com.example.weather.MainActivity
import com.example.weather.R

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = context.getString(R.string.reminders_notification_channel_id)
        )
        // Remove this line if you don't want to reschedule the reminder
        RemindersManager.startReminder(context.applicationContext)
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        // create android channel
        val androidChannel =
            NotificationChannel(
                applicationContext.getString(R.string.reminders_notification_channel_id),
                applicationContext.getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )

        val notificationManager: NotificationManager =
            applicationContext.getSystemService<NotificationManager>(
                NotificationManager::class.java
            )
        notificationManager.createNotificationChannel(androidChannel)
        androidChannel.enableLights(true)
        androidChannel.enableVibration(true)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                applicationContext,
                applicationContext.getString(R.string.reminders_notification_channel_id)
            )

        notificationManager.createNotificationChannel(androidChannel)

        builder.setContentTitle("Weather")
            .setContentText("check the weather in your city")
            .setSmallIcon(R.drawable.cloudy)

            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        notificationManager.notify(getUniqueID(), builder.build())
    }else{

        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(applicationContext)
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        builder.setSmallIcon(R.drawable.cloudy)
            .setSound(uri)
            .setAutoCancel(true)
            .setContentTitle("Weather")

            .setContentText("check the weather in your city")
            .setContentIntent(pendingIntent)
            .setLights(-0x10000, 100, 100)

        NotificationManagerCompat.from(applicationContext).notify(getUniqueID(), builder.build())

    }
}

private fun getUniqueID(): Int {
    val time = System.nanoTime().toString()
    val id = time.substring(time.length - 8, time.length)
    return id.toInt()
}
