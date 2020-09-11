package dev.anvith.blackbox.data

import android.content.Context
import dev.anvith.blackbox.extensions.fileName
import dev.anvith.blackbox.extensions.toInfo
import dev.anvith.blackbox.utils.writeToFile
import java.io.File


class FileStorage(private val context: Context) :
    Storage {
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
        get() = File(
            context.filesDir,
            DIR_NAME
        ).apply {
            if (!exists()) {
                mkdirs()
            }
        }

    companion object {
        const val DIR_NAME = "bbErrors"
    }
}
