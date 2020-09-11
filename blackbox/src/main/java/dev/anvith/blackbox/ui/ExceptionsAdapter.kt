package dev.anvith.blackbox.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import dev.anvith.blackbox.R
import dev.anvith.blackbox.data.ExceptionInfo
import dev.anvith.blackbox.extensions.DATE_FORMATTER
import dev.anvith.blackbox.extensions.getIntent
import dev.anvith.blackbox.extensions.toDisplayString
import java.util.Date

/**
 * Created by Anvith on 10/09/20.
 */
class ExceptionsAdapter(private val items: List<ExceptionInfo>) :
    RecyclerView.Adapter<ExceptionHolder>() {
    private val formatter = DATE_FORMATTER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExceptionHolder {
        return ExceptionHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_exception, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExceptionHolder, position: Int) {
        val item = items[position]
        holder.run {
            name.text = item.name
            trace.text = item.trace
            time.text = Date(item.occurredAt).toDisplayString(formatter)
            share.setOnClickListener {
                LocalBroadcastManager.getInstance(share.context).sendBroadcast(item.getIntent(true))
            }
            copy.setOnClickListener {
                LocalBroadcastManager.getInstance(copy.context).sendBroadcast(item.getIntent(false))
            }
        }
    }
}

class ExceptionHolder(view: View) : RecyclerView.ViewHolder(view) {
    val time: TextView = view.findViewById(R.id.time)
    val trace: TextView = view.findViewById(R.id.trace)
    val name: TextView = view.findViewById(R.id.name)
    val share: View = view.findViewById(R.id.share)
    val copy: View = view.findViewById(R.id.copy)
}
