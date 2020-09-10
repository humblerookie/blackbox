package com.hr.blackbox

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hr.blackbox.concurrency.PostNotificationTask
import com.hr.blackbox.data.Storage
import com.hr.blackbox.data.StorageProvider
import com.hr.blackbox.listeners.ActionsBroadcastListener
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.BB_ACTION_EVENT
import com.hr.blackbox.processor.BlackboxProcessor
import com.hr.blackbox.processor.BlackboxUncaughtExceptionHandler

/**
 * Created by Anvith on 09/09/20.
 */
@SuppressLint("StaticFieldLeak")
class Blackbox {

    companion object {
        private lateinit var ctx: Context
        private var secondaryExceptionHandlers: List<Thread.UncaughtExceptionHandler> = emptyList()

        fun context(context: Context): Companion {
            this.ctx = context.applicationContext
            return this
        }

        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Companion {
            this.secondaryExceptionHandlers = handlers
            return this
        }

        fun init() {
            if (!::ctx.isInitialized) {
                throw IllegalStateException("You need to provide an context for instantiation")
            }
            val storage = StorageProvider.getStorage(ctx)
            val exceptionHandler = BlackboxUncaughtExceptionHandler(
                secondaryExceptionHandlers,
                BlackboxProcessor(ctx, storage)
            )

            setExceptionHandler(exceptionHandler)
            initReceiver(ctx)
            showNotification(ctx, storage)
        }

        private fun setExceptionHandler(exceptionHandler: BlackboxUncaughtExceptionHandler) {
            if (Thread.getDefaultUncaughtExceptionHandler() !is BlackboxUncaughtExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
            }
        }

        private fun initReceiver(context: Context) {
            LocalBroadcastManager.getInstance(context)
                .registerReceiver(ActionsBroadcastListener(), IntentFilter(BB_ACTION_EVENT))
        }

        private fun showNotification(
            context: Context,
            storage: Storage
        ) {
            PostNotificationTask(context, storage).execute()
        }
    }

    class Builder {
        private lateinit var ctx: Context
        private var secondaryExceptionHandlers: List<Thread.UncaughtExceptionHandler> = emptyList()

        @Deprecated(
            message = "Use Blackbox class directly",
            replaceWith = ReplaceWith("Blackbox.context(context)")
        )
        fun context(context: Context): Builder {
            ctx = context
            return this
        }

        @Deprecated(message = "Use Blackbox class directly")
        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Builder {
            this.secondaryExceptionHandlers = handlers
            return this
        }

        @Deprecated(
            message = "Use Blackbox class directly"
        )
        fun init() {
            Blackbox.context(ctx)
                .init()
        }
    }
}
