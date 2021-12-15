package com.kc_hsu.podcastlite.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import com.youth.banner.indicator.BaseIndicator

/**
 * 矩形（長條形）指示器
 * 1、可以設置選中和默認的寬度、指示器的圓角
 * 2、如果需要正方形將圓角設置為0，可將寬度和高度設置為一樣
 * 3、如果不想選中時變長，可將選中的寬度和默認寬度設置為一樣
 */
class BannerIndicator internal constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseIndicator(context, attrs, defStyleAttr) {
    private val rectF: RectF = RectF()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = config.indicatorSize
        if (count <= 1) {
            return
        }
        // 間距 *（總數 - 1）+ 預設寬度 *（總數 - 1）+ 選中寬度
        val space = config.indicatorSpace * (count - 1)
        val normal = config.normalWidth * (count - 1)
        setMeasuredDimension(space + normal + config.selectedWidth, config.height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = config.indicatorSize
        if (count <= 1) {
            return
        }
        var left = 0f
        for (i in 0 until count) {
            mPaint.color =
                if (config.currentPosition == i) config.selectedColor else config.normalColor
            val indicatorWidth =
                if (config.currentPosition == i) config.selectedWidth else config.normalWidth
            rectF[left, 0f, left + indicatorWidth] = config.height.toFloat()
            left += (indicatorWidth + config.indicatorSpace).toFloat()
            canvas.drawRoundRect(rectF, config.radius.toFloat(), config.radius.toFloat(), mPaint)
        }
    }
}
