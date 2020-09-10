package com.hr.blackbox.concurrency

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.hr.blackbox.data.ExceptionInfo
import com.hr.blackbox.data.StorageProvider
import com.hr.blackbox.ui.ExceptionsAdapter
import java.lang.ref.WeakReference

/**
 * Its 2020 and we still need to use AsyncTask  🤦
 * since the Platform lacks better native concurrency primitives
 * */
class RetrieveFilesTask(
    context: Context,
    private val list: WeakReference<RecyclerView>,
    private val lifecycle: Lifecycle
) :
    AsyncTask<Void, Void, List<ExceptionInfo>>() {

    private val storage = StorageProvider.getStorage(context.applicationContext)
    override fun doInBackground(vararg params: Void?): List<ExceptionInfo> {

        return storage.getExceptions()
    }

    override fun onPostExecute(result: List<ExceptionInfo>) {
        super.onPostExecute(result)
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            list.get()?.adapter = ExceptionsAdapter(storage.getExceptions())
        }
    }
}
