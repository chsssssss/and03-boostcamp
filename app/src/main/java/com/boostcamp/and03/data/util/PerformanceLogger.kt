package com.boostcamp.and03.data.util

import android.os.SystemClock.elapsedRealtime

object PerformanceLogger {

    inline fun <T> measureLoadingTime(
        tag: String,
        block: () -> T
    ): T {
        val start = elapsedRealtime()
        return try {
            block()
        } finally {
            val end = elapsedRealtime()
            android.util.Log.d(tag, "${end - start} ms 경과")
        }
    }
}