package dev.anvith.blackbox.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.anvith.blackbox.sample.R.id
import dev.anvith.blackbox.sample.R.layout
import dev.anvith.blackbox.ui.ExceptionListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        initViews()
    }

    private fun initViews() {

        findViewById<View>(id.npe_exception).setOnClickListener {
            val ctx: Context? = null
            ctx!!.resources
        }

        findViewById<View>(id.index_exception).setOnClickListener {
            val list: List<String?> = mutableListOf("hello")
            val crashMe = list[2]
            Log.d(TAG, "$crashMe")
        }

        findViewById<View>(id.open_list).setOnClickListener {
           startActivity(Intent(this, ExceptionListActivity::class.java))
        }


    }

    companion object {
        const val TAG = "MainActivity"
    }
}