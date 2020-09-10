package com.hr.blackbox.data

import android.content.Context
import com.hr.blackbox.extensions.fileName
import com.hr.blackbox.extensions.toInfo
import com.hr.blackbox.extensions.writeToFile
import java.io.File

/**
 * Created by Anvith on 10/09/20.
 */

class FileStorage(private val context: Context) : Storage {
    override fun store(exception: ExceptionInfo) {
        exception.writeToFile(File(storageDirectory, exception.fileName()))
    }

    override fun getExceptions(): List<ExceptionInfo> {
        val files = storageDirectory.listFiles().toMutableList()
        files.sortByDescending { it.name }
        return files.mapNotNull { it.toInfo() }
    }

    override fun getLastException(): ExceptionInfo? {
        val file = storageDirectory.listFiles().toMutableList().apply {
            sortByDescending { it.lastModified() }
        }.firstOrNull()

        return file?.toInfo()
    }

    private val storageDirectory
        get() = File(context.filesDir, DIR_NAME).apply {
            if (!exists()) {
                mkdirs()
            }
        }

    companion object {
        const val DIR_NAME = "bbErrors"
    }
}
