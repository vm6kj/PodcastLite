package com.kc_hsu.podcastlite.ui.home.models

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import timber.log.Timber
import kotlin.math.abs

@SuppressWarnings("Just for testing")
@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BestPodcastCarousel(context: Context) : Carousel(context) {

    @ModelProp
    fun setAdjustHorizontalScroll(adjustHorizontalScroll: Boolean) {
        if (!adjustHorizontalScroll) {
            Timber.d("Actually no need to set adjustHorizontalScroll to false")
            return
        }

        // Reference: https://stackoverflow.com/a/55664229/7871271
        val y_buffer = 10
        var preX = 0f
        var preY = 0f
        addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> rv.parent.requestDisallowInterceptTouchEvent(true)
                    MotionEvent.ACTION_MOVE -> {
                        if (abs(e.x - preX) > abs(e.y - preY)) {
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        } else if (abs(e.y - preY) > y_buffer) {
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

    // you need to override this to prevent NPE of Carousel.setModels()
    @ModelProp
    override fun setModels(models: List<EpoxyModel<*>>) {
        // remove super method because we use PagedController for models build.
    }
}
