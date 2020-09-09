package com.hr.blackbox.data

import android.content.Context
import com.hr.blackbox.utils.getStacktraceString

/**
 * Created by Anvith on 09/09/20.
 */

interface Storage {

    fun store(exception: Throwable)

    fun getLastException(): Pair<String, String>?
}

class SharedPrefStorage(context: Context) : Storage {

    private val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    private val editor = sp.edit()

    override
    fun store(exception: Throwable) {
        editor.putString(LAST_EXCEPTION_NAME, exception.javaClass.name)
        editor.putString(LAST_EXCEPTION_TRACE, exception.getStacktraceString())
        editor.commit()
    }

    override fun getLastException(): Pair<String, String>? {
        val name = sp.getString(LAST_EXCEPTION_NAME, null)
        val trace = sp.getString(LAST_EXCEPTION_TRACE, null)
        return name?.let {
            Pair(name, trace!!)
        }
    }

    companion object {
        private const val PREF_KEY = "bb_shared_pref"
        private const val LAST_EXCEPTION_NAME = "last_exception_name"
        private const val LAST_EXCEPTION_TRACE = "last_exception_trace"

    }
}

class StorageProvider {

    companion object {

        fun getStorage(context: Context): Storage =
            SharedPrefStorage(context)
    }
}