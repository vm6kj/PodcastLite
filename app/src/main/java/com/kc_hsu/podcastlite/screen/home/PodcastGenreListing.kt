package com.kc_hsu.podcastlite.screen.home

import com.airbnb.epoxy.EpoxyController
import com.kc_hsu.podcastlite.utils.NetworkState

data class PodcastGenreListing(
    val loadingState: NetworkState? = null,
    val controller: EpoxyController,
    val itemCountOnScreen: Float = 0.0f
)
