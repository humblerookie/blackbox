package com.hr.blackbox

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

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
        }

    }
}