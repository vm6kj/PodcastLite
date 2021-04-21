package com.kc_hsu.podcastlite.customview

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

class SquareRoundedImageView : ShapeableImageView {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        shapeAppearanceModel = shapeAppearanceModel.toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
    }

    // Pass the same size of width and height with widthMeasureSpec
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
