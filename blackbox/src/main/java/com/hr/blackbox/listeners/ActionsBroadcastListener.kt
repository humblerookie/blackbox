package com.hr.blackbox.listeners

import android.content.*
import android.widget.Toast
import com.hr.blackbox.R


/**
 * Created by Anvith on 09/09/20.
 */
class ActionsBroadcastListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        when (intent.getStringExtra(ACTION_KEY)) {
            SHARE -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, intent.message)
                    putExtra(Intent.EXTRA_TITLE, intent.title)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(shareIntent)

            }
            COPY -> {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(intent.title, intent.message)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context,
                    R.string.bb_copied_confirmation, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private val Intent.title get() = getStringExtra(MSG_TITLE)
    private val Intent.message get() = getStringExtra(MSG_BODY)

    companion object {
        const val SHARE = "share"
        const val COPY = "copy"
        const val MSG_TITLE = "msg_title"
        const val MSG_BODY = "msg_body"
        const val ACTION_KEY = "action_key"
        const val BB_ACTION_EVENT = "bb_action_event"
    }
}