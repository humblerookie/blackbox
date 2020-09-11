package dev.anvith.blackbox

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dev.anvith.blackbox.concurrency.PostNotificationTask
import dev.anvith.blackbox.data.Storage
import dev.anvith.blackbox.data.StorageProvider
import dev.anvith.blackbox.listeners.ActionsBroadcastListener
import dev.anvith.blackbox.listeners.ActionsBroadcastListener.Companion.BB_ACTION_EVENT
import dev.anvith.blackbox.processor.BlackboxProcessor
import dev.anvith.blackbox.processor.BlackboxUncaughtExceptionHandler

/**
 * Created by Anvith on 09/09/20.
 */
@SuppressLint("StaticFieldLeak")
class Blackbox {

    companion object {
        private lateinit var ctx: Context
        private var secondaryExceptionHandlers: List<Thread.UncaughtExceptionHandler> = emptyList()

        fun context(context: Context): Companion {
            ctx = context.applicationContext
            return this
        }

        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Companion {
            secondaryExceptionHandlers = handlers
            return this
        }

        fun init() {
            if (!Companion::ctx.isInitialized) {
                throw IllegalStateException("You need to provide an context for instantiation")
            }
            val storage = StorageProvider.getStorage(
                ctx
            )
            val exceptionHandler = BlackboxUncaughtExceptionHandler(
                secondaryExceptionHandlers,
                BlackboxProcessor(ctx, storage)
            )

            setExceptionHandler(
                exceptionHandler
            )
            initReceiver(
                ctx
            )
            showNotification(
                ctx,
                storage
            )
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
            PostNotificationTask(
                context,
                storage
            ).execute()
        }
    }
}
