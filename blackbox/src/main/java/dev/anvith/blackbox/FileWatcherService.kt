package dev.anvith.blackbox

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.FileObserver
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dev.anvith.blackbox.concurrency.PostNotificationTask
import dev.anvith.blackbox.data.FileStorage
import dev.anvith.blackbox.data.StorageProvider
import dev.anvith.blackbox.listeners.ActionsBroadcastListener
import dev.anvith.blackbox.utils.createNotification

/**
 * Created by Anvith on 12/09/20.
 */
class FileWatcherService : Service() {
    private lateinit var observer: FileObserver
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
            ID,
            applicationContext.createNotification(
                StorageProvider.getStorage(this).getLastException()
            )
        )
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        startFileWatcher()
        registerReceiver(applicationContext)
    }

    private fun startFileWatcher() {
        val storagePath = FileStorage.getStorageDirectory(this).absolutePath
        observer = object : FileObserver(storagePath) {
            override fun onEvent(event: Int, file: String?) {
                if (event == CLOSE_WRITE && file != null) {
                    val ctx = this@FileWatcherService.applicationContext
                    PostNotificationTask(ctx, StorageProvider.getStorage(ctx)).execute()
                }
            }
        }
        observer.startWatching()
    }

    private fun registerReceiver(context: Context) {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(
                ActionsBroadcastListener(),
                IntentFilter(ActionsBroadcastListener.BB_ACTION_EVENT)
            )
    }

    companion object {
        const val ID = 1
    }
}
