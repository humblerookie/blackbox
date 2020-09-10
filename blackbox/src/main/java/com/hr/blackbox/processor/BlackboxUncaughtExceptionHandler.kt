package com.hr.blackbox.processor

import android.util.Log

/**
 * Created by Anvith on 09/09/20.
 */

class BlackboxUncaughtExceptionHandler(
    private val secondaryExceptionHandlers: List<Thread.UncaughtExceptionHandler> = emptyList(),
    private val processor: BlackboxProcessor
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        Log.e(TAG, exception.toString())
        processor.process(thread, exception)
        callSecondaryHandlers(thread, exception)
    }

    private fun callSecondaryHandlers(thread: Thread, exception: Throwable) {
        secondaryExceptionHandlers.forEach {
            it.uncaughtException(thread, exception)
        }
    }

    companion object {
        const val TAG = "Blackbox"
    }
}
