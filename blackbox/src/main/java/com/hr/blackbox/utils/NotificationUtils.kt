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
import com.hr.blackbox.data.Constants.PENDING_REQUEST_ID3
import com.hr.blackbox.data.ExceptionInfo
import com.hr.blackbox.listeners.ActionsBroadcastListener
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.ACTION_KEY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.BB_ACTION_EVENT
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.COPY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.MSG_BODY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.MSG_TITLE
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.SHARE
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.VIEW_ALL

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

fun Context.createNotification(throwableInfo: ExceptionInfo? = null): Notification {
    val description = throwableInfo?.name ?: getString(R.string.bb_placeholder_notifications)
    return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.bb_ic_broken)
        .setContentTitle(getString(R.string.bb_title))
        .setContentText(description)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(throwableInfo?.trace.orEmpty())
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT).apply {
            if (throwableInfo != null) {
                addAction(
                    android.R.drawable.ic_menu_share,
                    getString(R.string.bb_share),
                    throwableInfo.getActionPendingIntent(this@createNotification, SHARE)
                )

                addAction(
                    R.drawable.ic_copy,
                    getString(R.string.bb_copy),
                    throwableInfo.getActionPendingIntent(this@createNotification, COPY)
                )

                addAction(
                    R.drawable.ic_all,
                    getString(R.string.bb_view_all),
                    throwableInfo.getActionPendingIntent(this@createNotification, VIEW_ALL)
                )
            }
        }
        .build()
}

private fun String.toRequestId(): Int = when (this) {
    SHARE -> PENDING_REQUEST_ID1
    COPY -> PENDING_REQUEST_ID2
    else -> PENDING_REQUEST_ID3
}

private fun ExceptionInfo.getActionPendingIntent(
    context: Context,
    actionId: String
): PendingIntent {
    val broadcastEvent =
        Intent(context.applicationContext, ActionsBroadcastListener::class.java).apply {
            action = BB_ACTION_EVENT
            putExtra(MSG_TITLE, name)
            putExtra(MSG_BODY, trace)
            putExtra(ACTION_KEY, actionId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

    return PendingIntent.getBroadcast(
        context,
        actionId.toRequestId(),
        broadcastEvent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}
