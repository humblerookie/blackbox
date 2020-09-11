package dev.anvith.blackbox.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dev.anvith.blackbox.R
import dev.anvith.blackbox.concurrency.RetrieveFilesTask
import java.lang.ref.WeakReference

/**
 * Created by Anvith on 10/09/20.
 */

class ExceptionListActivity : AppCompatActivity() {

    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception_list)
        list = findViewById(R.id.list)
        fetchExceptions()
    }

    private fun fetchExceptions() {
        RetrieveFilesTask(
            applicationContext,
            WeakReference(list),
            lifecycle
        ).execute()
    }
}
