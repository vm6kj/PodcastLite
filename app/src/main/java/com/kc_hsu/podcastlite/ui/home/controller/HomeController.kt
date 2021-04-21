package com.kc_hsu.podcastlite.ui.home.controller

import com.airbnb.epoxy.EpoxyController
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.TitleOfCategoryBindingModel_
import com.kc_hsu.podcastlite.ui.home.models.BestPodcastCarouselModel_

class HomeController : EpoxyController() {

    var controller: EpoxyController? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    // UI arrangement logic
    override fun buildModels() {
        TitleOfCategoryBindingModel_()
            .id("category_title")
            .title("KCTEST_QQ")
            .addTo(this)
        BestPodcastCarouselModel_()
            .id("category_carousel")
            .models(emptyList())
            .adjustHorizontalScroll(true)
            .epoxyController(controller!!)
            .paddingRes(R.dimen.size_8_dp)
            .numViewsToShowOnScreen(3.1F)
            .addTo(this)
    }
}
