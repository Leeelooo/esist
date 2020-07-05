package com.leeloo.esist.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.leeloo.esist.EsistApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

fun init(app: EsistApp) {
//    DaggerAppComponent.builder()
//            .application(app)
//            .build().inject(app)
//    app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
//        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
//            handleActivity(activity)
//        }
//        override fun onActivityStarted(activity: Activity) {}
//        override fun onActivityResumed(activity: Activity) {}
//        override fun onActivityPaused(activity: Activity) {}
//        override fun onActivityStopped(activity: Activity) {}
//        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
//        override fun onActivityDestroyed(activity: Activity) {}
//    })
}

private fun handleActivity(activity: Activity) {
    AndroidInjection.inject(activity)
    if (activity is FragmentActivity) {
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentAttached(
                            fm: FragmentManager,
                            f: Fragment,
                            context: Context
                    ) {
                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }
                    }
                }, true
        )
    }
}