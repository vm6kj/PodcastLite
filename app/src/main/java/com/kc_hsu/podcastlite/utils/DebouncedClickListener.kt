package com.kc_hsu.podcastlite.utils

import android.os.SystemClock
import android.view.View
import timber.log.Timber
import java.util.WeakHashMap

abstract class DebouncedClickListener(private val minimumInterval: Long = 400) : View.OnClickListener {
    private val lastClickMap = WeakHashMap<View, Long>()

    abstract fun onDebouncedClick(v: View)

    override fun onClick(v: View) {
        val previousClickTimestamp = lastClickMap[v] ?: 0
        val currentTimestamp = SystemClock.elapsedRealtime()
        lastClickMap[v] = currentTimestamp

        if (currentTimestamp > previousClickTimestamp + minimumInterval) {
            onDebouncedClick(v)
        } else {
            Timber.v("Debounced click!")
        }
    }
}
