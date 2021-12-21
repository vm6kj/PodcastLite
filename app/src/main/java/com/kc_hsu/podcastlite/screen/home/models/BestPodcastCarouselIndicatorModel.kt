package com.kc_hsu.podcastlite.screen.home.models

import com.kc_hsu.podcastlite.screen.helpers.CirclePagerIndicatorDecoration

@SuppressWarnings("Unused")
class BestPodcastCarouselIndicatorModel : BestPodcastCarouselModel_() {
    private val indicator = CirclePagerIndicatorDecoration()

    override fun bind(carousel: BestPodcastCarousel) {
        super.bind(carousel)
        carousel.addItemDecoration(indicator)
    }

    override fun unbind(carousel: BestPodcastCarousel) {
        super.unbind(carousel)
        carousel.removeItemDecoration(indicator)
    }
}
