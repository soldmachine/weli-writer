package com.szoldapps.weli.writer.other

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

internal class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority != Log.VERBOSE) logMessage(tag, message)

        if (priority == Log.ERROR && t != null) {
            Firebase.crashlytics.recordException(t)
        }
    }

    private fun logMessage(tag: String?, message: String) {
        val messageToLog = tag?.let { "$tag: $message" } ?: message
        Firebase.crashlytics.log(messageToLog)
    }
}
