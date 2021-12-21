package com.kc_hsu.podcastlite.screen.home.controller

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.TitleOfCategoryBindingModel_
import com.kc_hsu.podcastlite.screen.home.PodcastGenreListing
import com.kc_hsu.podcastlite.screen.home.PodcastGenres
import com.kc_hsu.podcastlite.screen.home.models.BestPodcastCarouselModel_

class HomeController(private val context: Context) : EpoxyController() {

    var podcastGenreListings: Map<PodcastGenres, PodcastGenreListing>? = null

    // UI arrangement logic
    override fun buildModels() {
        podcastGenreListings?.forEach { (genre, listing) ->
            TitleOfCategoryBindingModel_()
                .id(genre.genreId)
                .title(context.getString(genre.stringRes))
                .addTo(this)
            BestPodcastCarouselModel_()
                .id("${genre.genreId}_carousel")
                .models(emptyList())
                .adjustHorizontalScroll(true)
                .epoxyController(listing.controller)
                // Can use 3rd party snap helper like https://github.com/rubensousa/GravitySnapHelper
                .customDefaultGlobalSnapHelperFactory(null)
                .paddingRes(R.dimen.size_8_dp)
                .numViewsToShowOnScreen(listing.itemCountOnScreen)
                .addTo(this)
        }
    }
}
