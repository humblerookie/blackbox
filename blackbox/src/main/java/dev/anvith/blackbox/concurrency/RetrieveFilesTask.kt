package dev.anvith.blackbox.concurrency

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import dev.anvith.blackbox.data.ExceptionInfo
import dev.anvith.blackbox.data.StorageProvider
import dev.anvith.blackbox.ui.ExceptionsAdapter
import java.lang.ref.WeakReference

/**
 * Its 2020 and we still need to use AsyncTask 🤦
 * since the Platform lacks better native concurrency primitives
 * @param context Required for accessing storage
 * @param list To post the updated exception list on.
 * @param lifecycle Check in case results don't have to be delivered
 */
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
