package com.google.android.material.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import java.lang.ref.WeakReference

/**
 * Override {@link #findScrollingChild(View)} to support {@link ViewPager}'s nested scrolling.
 *
 * By the way, In order to override package level method and field.
 * This class put in the same package path where {@link BottomSheetBehavior} located.
 *
 * Reference: https://hanru-yeh.medium.com/funny-solution-that-makes-bottomsheetdialog-support-viewpager-with-nestedscrollingchilds-bfdca72235c3
 */
open class ViewPagerBottomSheetBehavior<V : View>(context: Context, attrs: AttributeSet?) :
    BottomSheetBehavior<V>(context, attrs) {

    override fun findScrollingChild(view: View?): View? {

        view?.let {
            if (ViewCompat.isNestedScrollingEnabled(it)) {
                return it
            }
        }

        when (view) {
            is ViewPager2 -> {
                val currentViewPagerChild = view.getChildAt(view.currentItem)
                val scrollingChild = findScrollingChild(currentViewPagerChild)

                if (scrollingChild != null) {
                    return scrollingChild
                }
            }

            is ViewGroup -> {
                var i = 0
                val count: Int = view.childCount
                while (i < count) {
                    val scrollingChild = findScrollingChild(view.getChildAt(i))
                    if (scrollingChild != null) {
                        return scrollingChild
                    }
                    i++
                }
            }
        }

        return null
    }

    fun updateScrollingChild() {
        val scrollingChild = findScrollingChild(viewRef?.get())
        nestedScrollingChildRef = WeakReference(scrollingChild)
    }
}
