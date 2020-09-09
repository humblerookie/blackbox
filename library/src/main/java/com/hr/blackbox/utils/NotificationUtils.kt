package com.hr.blackbox.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.hr.blackbox.R
import com.hr.blackbox.data.Constants.NOTIFICATION_CHANNEL_ID
import com.hr.blackbox.data.Constants.PENDING_REQUEST_ID1
import com.hr.blackbox.data.Constants.PENDING_REQUEST_ID2
import com.hr.blackbox.listeners.ActionsBroadcastListener
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.ACTION_KEY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.BB_ACTION_EVENT
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.COPY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.MSG_BODY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.MSG_TITLE
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.SHARE

/**
 * Created by Anvith on 09/09/20.
 */

val Context.notificationManager: NotificationManager
    get() {
        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

fun Context.createNotificationChannel() {
    val channelExists = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null
    } else {
        false
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !channelExists) {
        // Create the NotificationChannel
        val name = getString(R.string.bb_channel_name)
        val descriptionText = getString(R.string.bb_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this

        notificationManager.createNotificationChannel(channel)
    }
}

fun Context.createNotification(throwableInfo: Pair<String, String>? = null): Notification {
    val description = throwableInfo?.first ?: getString(R.string.bb_placeholder_notifications)
    return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.bb_ic_broken)
        .setContentTitle(getString(R.string.bb_title))
        .setContentText(description)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(throwableInfo?.second.orEmpty())
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT).apply {
            if (throwableInfo != null) {
                addAction(
                    android.R.drawable.ic_menu_share,
                    getString(R.string.bb_share),
                    throwableInfo.getActionPendingIntent(this@createNotification, SHARE)
                )

                addAction(
                    android.R.drawable.gallery_thumb,
                    getString(R.string.bb_copy),
                    throwableInfo.getActionPendingIntent(this@createNotification, COPY)
                )
            }
        }
        .build()


}

private fun Pair<String, String>.getActionPendingIntent(
    context: Context,
    actionId: String
): PendingIntent {
    val broadcastEvent =
        Intent(context.applicationContext, ActionsBroadcastListener::class.java).apply {
            action = BB_ACTION_EVENT
            putExtra(MSG_TITLE, first)
            putExtra(MSG_BODY, second)
            putExtra(ACTION_KEY, actionId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

    val requestId = if (actionId == SHARE) PENDING_REQUEST_ID1 else PENDING_REQUEST_ID2
    return PendingIntent.getBroadcast(
        context,
        requestId,
        broadcastEvent,
        0
    )
}