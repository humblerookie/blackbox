package dev.anvith.blackbox.util

import android.util.Log
import dev.anvith.blackbox.extensions.toInfo
import dev.anvith.blackbox.utils.getStacktraceString
import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * Created by anvith on 27/09/20.
 */

class DataExtensionTest {

    @Test
    fun `throwable info has proper stacktrace`(){

        val throwable =IllegalArgumentException("Something went wrong")

        val stacktrace=throwable.getStacktraceString()

        assert(throwable.toInfo().trace == stacktrace)

    }
}