package com.hr.blackbox

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

    class Builder {
        @Deprecated(
            message = "Use Blackbox class directly",
            replaceWith = ReplaceWith("Blackbox.context(context)")
        )
        fun context(context: Context): Builder {
            return this
        }

        @Deprecated(message = "Use Blackbox class directly")
        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Builder {
            return this
        }

        fun init(): Blackbox {
            return Blackbox()
        }
    }
}
