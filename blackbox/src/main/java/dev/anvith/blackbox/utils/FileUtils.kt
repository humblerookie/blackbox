package dev.anvith.blackbox.utils

import android.util.Log
import dev.anvith.blackbox.data.ExceptionInfo
import dev.anvith.blackbox.extensions.asFileContent
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.jvm.Throws

/**
 * Created by Anvith on 11/09/20.
 */

@Throws(Exception::class)
fun convertStreamToString(`is`: InputStream?): String {
    val reader = BufferedReader(InputStreamReader(`is`))
    val sb = StringBuilder()
    var line: String?
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
