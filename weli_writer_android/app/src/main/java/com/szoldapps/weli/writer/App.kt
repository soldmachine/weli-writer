package com.szoldapps.weli.writer

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

import timber.log.Timber.DebugTree


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            TODO("add CrashReportingTree")
        }
    }
}
