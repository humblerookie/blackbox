package dev.anvith.blackbox

import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dev.anvith.blackbox.Blackbox.Companion.init
import dev.anvith.blackbox.concurrency.PostNotificationTask
import dev.anvith.blackbox.data.Storage
import dev.anvith.blackbox.data.StorageProvider
import dev.anvith.blackbox.listeners.ActionsBroadcastListener
import dev.anvith.blackbox.listeners.ActionsBroadcastListener.Companion.BB_ACTION_EVENT
import dev.anvith.blackbox.processor.BlackboxProcessor
import dev.anvith.blackbox.processor.BlackboxUncaughtExceptionHandler

/**
 * This is the entry point into the library
 * @see init
 * for initializing the library
 */
class Blackbox {

    companion object {
        private lateinit var ctx: Context
        private var secondaryExceptionHandlers: List<Thread.UncaughtExceptionHandler> = emptyList()

        /**
         * Provide a [Context] instance for accessing file resources and others.
         * This does not need to be an application context.
         *
         * Note: This is a mandatory provision
         */

        fun context(context: Context): Companion {
            ctx = context.applicationContext
            return this
        }

        /**
         * Provide a list of other [Thread.UncaughtExceptionHandler]s which may perform actions
         * like remote logging for eg in case of Firebase error logging.
         * @param handlers The list of handlers to also forward the error to
         */

        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Companion {
            secondaryExceptionHandlers = handlers
            return this
        }


        /**
         * This is the entry point into the library.
         * Invoking this initializes all the relevant components including the initial notification.
         */

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
