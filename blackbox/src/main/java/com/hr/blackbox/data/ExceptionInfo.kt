package com.hr.blackbox.data

/**
 * Created by Anvith on 10/09/20.
 */

data class ExceptionInfo(
    val occurredAt: Long,
    val name: String,
    val trace: String
)
