package com.kc_hsu.podcastlite.screen.home

import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody

interface PodcastClickListener {
    fun onPodcastClick(podcast: BestPodcastsBody.Podcast)
}

interface SettingClickListener {
    fun onSettingClick()
}
