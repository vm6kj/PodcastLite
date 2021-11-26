package com.kc_hsu.podcastlite.ui.view

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2

class NestedScrollableHost(context: Context) : FrameLayout(context) {
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while(v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    private val childView: View?
        get() {
            return if (childCount > 0) {
                getChildAt(0)
            } else {
                null
            }
        }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }
}
