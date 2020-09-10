package com.hr.blackbox.extensions

import android.content.Intent
import android.util.Log
import com.hr.blackbox.data.ExceptionInfo
import com.hr.blackbox.listeners.ActionsBroadcastListener
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.COPY
import com.hr.blackbox.listeners.ActionsBroadcastListener.Companion.SHARE
import com.hr.blackbox.utils.getStacktraceString
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.UUID

/**
 * Created by Anvith on 10/09/20.
 */

private const val TAG = "DataExtensions"

private const val SEPARATOR = "\n"

fun Throwable.toInfo(): ExceptionInfo {
    return ExceptionInfo(System.currentTimeMillis(), javaClass.name, getStacktraceString())
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

@Throws(Exception::class)
fun convertStreamToString(`is`: InputStream?): String {
    val reader = BufferedReader(InputStreamReader(`is`))
    val sb = StringBuilder()
    var line: String? = null
    while (reader.readLine().also { line = it } != null) {
        sb.append(line).append("\n")
    }
    reader.close()
    return sb.toString()
}

@Throws(Exception::class)
fun File.getStringFromFile(): String? {
    val fin = FileInputStream(this)
    val ret = convertStreamToString(fin)
    // Make sure you close all streams.
    fin.close()
    return ret
}

fun ExceptionInfo.writeToFile(file: File) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(FileOutputStream(file))
        outputStreamWriter.write(this.asFileContent())
        outputStreamWriter.close()
    } catch (e: IOException) {
        Log.e("Exception", "File write failed: $e")
    }
}
