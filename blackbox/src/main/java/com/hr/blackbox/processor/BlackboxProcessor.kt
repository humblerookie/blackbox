package com.hr.blackbox.processor

import android.content.Context
import com.hr.blackbox.data.Storage
import com.hr.blackbox.extensions.toInfo
import com.hr.blackbox.utils.createNotificationChannel

/**
 * Created by Anvith on 09/09/20.
 */
class BlackboxProcessor(context: Context, private val storage: Storage) {

    init {
        context.createNotificationChannel()
    }

    private val errors = mutableListOf<Pair<String, Throwable>>()

    fun process(thread: Thread, exception: Throwable) {
        errors.add(Pair(thread.toString(), exception))
        storage.store(exception.toInfo())
    }
}
