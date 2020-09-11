package dev.anvith.blackbox.data

import android.content.Context

/**
 * Created by Anvith on 09/09/20.
 */

interface Storage {

    fun store(exception: ExceptionInfo)

    fun getExceptions(): List<ExceptionInfo>

    fun getLastException(): ExceptionInfo?
}

class StorageProvider {

    companion object {
        private lateinit var storage: FileStorage
        fun getStorage(context: Context): Storage {
            if (!Companion::storage.isInitialized) {
                storage =
                    FileStorage(context)
            }
            return storage
        }
    }
}
