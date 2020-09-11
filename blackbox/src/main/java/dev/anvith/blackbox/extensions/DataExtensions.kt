package dev.anvith.blackbox.extensions

import android.content.Intent
import android.util.Log
import dev.anvith.blackbox.data.ExceptionInfo
import dev.anvith.blackbox.listeners.ActionsBroadcastListener
import dev.anvith.blackbox.listeners.ActionsBroadcastListener.Companion.COPY
import dev.anvith.blackbox.listeners.ActionsBroadcastListener.Companion.SHARE
import dev.anvith.blackbox.utils.getStacktraceString
import dev.anvith.blackbox.utils.getStringFromFile
import java.io.File
import java.util.UUID

/**
 * Created by Anvith on 10/09/20.
 */

private const val TAG = "DataExtensions"

private const val SEPARATOR = "\n"

fun Throwable.toInfo(): ExceptionInfo {
    return ExceptionInfo(
        System.currentTimeMillis(),
        javaClass.name,
        getStacktraceString()
    )
}

fun ExceptionInfo.asFileContent(): String {
    return "$occurredAt$SEPARATOR$name$SEPARATOR$trace"
}

fun ExceptionInfo.fileName(): String {
    return "$occurredAt${UUID.randomUUID()}.log"
}

fun File.toInfo(): ExceptionInfo? {
    try {
        val contents = getStringFromFile()?.split(SEPARATOR)
        return contents?.let {
            ExceptionInfo(
                contents[0].toLong(),
                contents[1],
                contents.subList(2, contents.size).joinToString(SEPARATOR)
            )
        }
    } catch (e: Exception) {
        Log.e(TAG, e.toString())
    }
    return null
}

fun ExceptionInfo.getIntent(isShare: Boolean): Intent {
    return Intent(ActionsBroadcastListener.BB_ACTION_EVENT).apply {
        putExtra(ActionsBroadcastListener.MSG_TITLE, name)
        putExtra(ActionsBroadcastListener.MSG_BODY, trace)
        putExtra(ActionsBroadcastListener.ACTION_KEY, if (isShare) SHARE else COPY)
    }
}
