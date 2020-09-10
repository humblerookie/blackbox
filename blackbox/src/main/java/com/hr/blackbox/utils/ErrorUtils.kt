package com.hr.blackbox.utils

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by Anvith on 09/09/20.
 */

fun Throwable.getStacktraceString(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    printStackTrace(pw)
    return sw.toString()
}
