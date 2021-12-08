package com.szoldapps.weli.writer

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.szoldapps.weli.writer.other.CrashReportingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        val tree = if (BuildConfig.DEBUG) DebugTree() else CrashReportingTree()
        Timber.plant(tree)
    }
}
