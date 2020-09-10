package com.hr.blackbox.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Anvith on 10/09/20.
 */

private const val DISPLAY_FORMAT = "dd/MM/yyyy hh:mm:ssa"

val DATE_FORMATTER get() = SimpleDateFormat(DISPLAY_FORMAT, Locale.getDefault())

fun Date.toDisplayString(formatter: SimpleDateFormat): String {
    return formatter.format(this)
}
