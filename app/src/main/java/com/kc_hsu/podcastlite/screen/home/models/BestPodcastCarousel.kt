package com.kc_hsu.podcastlite.screen.home.models

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.*
import timber.log.Timber
import kotlin.math.abs

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BestPodcastCarousel(context: Context) : Carousel(context) {

    @ModelProp
    fun setAdjustHorizontalScroll(adjustHorizontalScroll: Boolean) {
        if (!adjustHorizontalScroll) {
            Timber.d("Actually no need to set adjustHorizontalScroll to false")
            return
        }

        // Reference: https://stackoverflow.com/a/55664229/7871271
        val yBuffer = 10
        var preX = 0f
        var preY = 0f
        addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> rv.parent.requestDisallowInterceptTouchEvent(true)
                    MotionEvent.ACTION_MOVE -> {
                        if (abs(e.x - preX) > abs(e.y - preY)) {
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        } else if (abs(e.y - preY) > yBuffer) {
                            rv.parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                }
                preX = e.x
                preY = e.y
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setEpoxyController(controller: EpoxyController) {
        setControllerAndBuildModels(controller)
    }

    // Reference: https://github.com/airbnb/epoxy/issues/328
    @ModelProp(ModelProp.Option.DoNotHash)
    fun setCustomDefaultGlobalSnapHelperFactory(snapHelperFactory: SnapHelperFactory?) {
        setDefaultGlobalSnapHelperFactory(snapHelperFactory)
    }

    // you need to override this to prevent NPE of Carousel.setModels()
    @ModelProp
    override fun setModels(models: List<EpoxyModel<*>>) {
        // remove super method because we use PagedController for models build.
    }
}
