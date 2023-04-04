package dev.anvith.blackbox.sample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.anvith.blackbox.sample.R.id
import dev.anvith.blackbox.sample.R.layout
import dev.anvith.blackbox.ui.ExceptionListActivity

private const val MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS = 456
private const val REQUEST_KEY = "request"

class MainActivity : AppCompatActivity() {

    private var requestType: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        requestType = savedInstanceState?.getInt(REQUEST_KEY)
        checkAndPromptNotificationPermission {}
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        requestType?.let {
            outState.putInt(REQUEST_KEY, it)
        }
    }

    private fun checkAndPromptNotificationPermission(postAction: () -> Unit) {
        if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU
            && ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
            )
        } else {
            postAction()
        }
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
            && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && requestType != null
        ) {
            triggerError()
        }
        requestType = null
    }


    private fun initViews() {

        findViewById<View>(id.npe_exception).setOnClickListener {
            requestType = 1
            checkAndPromptNotificationPermission(::triggerError)
        }

        findViewById<View>(id.index_exception).setOnClickListener {
            requestType = 2
            checkAndPromptNotificationPermission(::triggerError)
        }

        findViewById<View>(id.open_list).setOnClickListener {
            startActivity(Intent(this, ExceptionListActivity::class.java))
        }


    }

    private fun triggerError() {
        when (requestType) {
            1 -> triggerNpe()
            2 -> triggerOutOfBoundsError()
        }
        requestType = null
    }

    private fun triggerOutOfBoundsError() {
        val list: List<String?> = mutableListOf("hello")
        val crashMe = list[2]
        Log.d(TAG, "$crashMe")
    }

    private fun triggerNpe() {
        val ctx: Context? = null
        ctx!!.resources
    }

    companion object {
        const val TAG = "MainActivity"
    }
}