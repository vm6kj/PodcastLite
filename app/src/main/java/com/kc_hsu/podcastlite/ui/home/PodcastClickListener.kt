package com.kc_hsu.podcastlite.ui.home

import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody

interface PodcastClickListener {
    fun onPodcastClick(podcast: BestPodcastsBody.Podcast)
}
