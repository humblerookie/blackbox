package dev.anvith.blackbox

import android.content.Context

/**
 * A dummy no op class to avoid compilation issues
 */
class Blackbox {

    companion object {
        fun context(context: Context): Companion {
            return this
        }

        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Companion {
            return this
        }

        fun init() {
        }
    }
}
