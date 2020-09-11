package dev.anvith.blackbox.data


data class ExceptionInfo(
    val occurredAt: Long,
    val name: String,
    val trace: String
)
