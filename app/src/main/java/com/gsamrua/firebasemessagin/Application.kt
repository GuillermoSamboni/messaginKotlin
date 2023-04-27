package com.gsamrua.firebasemessagin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Toast

class MyApplication : Application(), Application.ActivityLifecycleCallbacks {

    private var isAppInForeground = false

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        isAppInForeground = true
    }

    override fun onActivityStarted(activity: Activity) {
        isAppInForeground = true
    }

    override fun onActivityResumed(p0: Activity) {
        isAppInForeground = true
    }

    override fun onActivityPaused(p0: Activity) {
        isAppInForeground = false
    }

    override fun onActivityStopped(activity: Activity) {
        isAppInForeground = false
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        isAppInForeground = true
    }

    override fun onActivityDestroyed(p0: Activity) {
        isAppInForeground = false
    }

    fun isAppInForeground(): Boolean {
        return isAppInForeground
    }
}