package com.hr.blackbox

import android.app.Application

/**
 * Created by Anvith on 08/09/20.
 */
class BlackboxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            //initialise reporter with external path
            Blackbox.context(this)
                .init()
        }
    }
}
