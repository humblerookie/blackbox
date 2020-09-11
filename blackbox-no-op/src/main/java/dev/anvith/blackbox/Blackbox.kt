package dev.anvith.blackbox

import android.content.Context

/**
 * Created by Anvith on 09/09/20.
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
