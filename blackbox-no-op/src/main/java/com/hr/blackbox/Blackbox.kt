package com.hr.blackbox

import android.content.Context

/**
 * Created by Anvith on 09/09/20.
 */
class Blackbox {
    class Builder {
        fun context(context: Context): Builder {
            return this
        }

        fun otherUncaughtExceptionHandlers(handlers: List<Thread.UncaughtExceptionHandler>): Builder {
            return this
        }

        fun init(): Blackbox {
            return Blackbox()
        }
    }
}