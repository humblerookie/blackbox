package com.hr.blackbox.concurrency

import android.content.Context
import android.os.AsyncTask
import com.hr.blackbox.data.Constants
import com.hr.blackbox.data.ExceptionInfo
import com.hr.blackbox.data.Storage
import com.hr.blackbox.utils.createNotification
import com.hr.blackbox.utils.notificationManager

/**
 * Its 2020 and we still need to use AsyncTask 🤦
 * since the Platform lacks better native concurrency primitives
 * */
class PostNotificationTask(context: Context, private val storage: Storage) :
    AsyncTask<Void, Void, ExceptionInfo?>() {
    private val ctx = context.applicationContext

    override fun doInBackground(vararg params: Void?): ExceptionInfo? {
        return storage.getLastException()
    }

    override fun onPostExecute(result: ExceptionInfo?) {
        super.onPostExecute(result)
        ctx.notificationManager.notify(
            Constants.NOTIFICATION_ID,
            ctx.createNotification(result)
        )
    }
}
