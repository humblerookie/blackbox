package dev.anvith.blackbox.sample

import android.app.Application
import dev.anvith.blackbox.Blackbox


/**
 * Created by Anvith on 08/09/20.
 */
class BlackboxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //initialise reporter with external path
        Blackbox.context(this).init()
    }
}
