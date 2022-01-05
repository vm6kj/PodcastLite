package com.kc_hsu.podcastlite.screen.helpers

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class CarouselSpacesItemDecoration(@Px private val innerSpace: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)

        outRect.left = if (itemPosition == 0) innerSpace else innerSpace / 2
        outRect.right = if (itemPosition == state.itemCount - 1) innerSpace else innerSpace / 2
    }
}
