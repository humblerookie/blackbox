package com.hr.blackbox

import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hr.blackbox.data.Constants.NOTIFICATION_ID
import com.hr.blackbox.data.Storage
import com.hr.blackbox.data.StorageProvider
import com.hr.blackbox.listeners.ActionsBroadcastListener
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.BB_ACTION_EVENT
import com.hr.blackbox.processor.BlackboxProcessor
import com.hr.blackbox.processor.BlackboxUncaughtExceptionHandler
import com.hr.blackbox.utils.createNotification
import com.hr.blackbox.utils.notificationManager

/**
 * Created by Anvith on 09/09/20.
 */
class Blackbox(
    context: Context,
    exceptionHandler: BlackboxUncaughtExceptionHandler,
    storage: Storage
) {
    init {
        setExceptionHandler(exceptionHandler)
        initReceiver(context)
        retreiveLastExceptionAndShowMessage(context, storage)
    }

    private fun retreiveLastExceptionAndShowMessage(
        context: Context,
        storage: Storage
    ) {
        val notification = context.applicationContext.createNotification(storage.getLastException())
        context.notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun initReceiver(context: Context) {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(ActionsBroadcastListener(), IntentFilter(BB_ACTION_EVENT))
    }

    private fun setExceptionHandler(exceptionHandler: BlackboxUncaughtExceptionHandler) {
        if (Thread.getDefaultUncaughtExceptionHandler() !is BlackboxUncaughtExceptionHandler) {
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
        }
    }

    class Builder {
        private lateinit var ctx: Context
        private var secondaryExceptionHandlers: List<Thread.UncaughtExceptionHandler> = emptyList()

        fun context(context: Context): Builder {
            ctx = context
            return this
        }

        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Builder {
            this.secondaryExceptionHandlers = handlers
            return this
        }

        fun init(): Blackbox {
            if (!::ctx.isInitialized) {
                throw IllegalStateException("You need to provide an context for instantiation")
            }
            val storage = StorageProvider.getStorage(ctx)
            return Blackbox(
                ctx,
                BlackboxUncaughtExceptionHandler(
                    secondaryExceptionHandlers,
                    BlackboxProcessor(ctx, storage)
                ),
                storage
            )
        }
    }
}