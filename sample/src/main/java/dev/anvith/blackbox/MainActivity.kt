package dev.anvith.blackbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.anvith.blackbox.ui.ExceptionListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {

        findViewById<View>(R.id.npe_exception).setOnClickListener {
            val ctx: Context? = null
            ctx!!.resources
        }

        findViewById<View>(R.id.index_exception).setOnClickListener {
            val list: List<String?> = mutableListOf("hello")
            val crashMe = list[2]
            Log.d(TAG, "$crashMe")
        }

        findViewById<View>(R.id.open_list).setOnClickListener {
           startActivity(Intent(this, ExceptionListActivity::class.java))
        }


    }

    companion object {
        const val TAG = "MainActivity"
    }
}