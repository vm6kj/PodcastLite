package com.kc_hsu.podcastlite.ui.home.models

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PodcastsRecommendationCarousel(context: Context) : Carousel(context)
