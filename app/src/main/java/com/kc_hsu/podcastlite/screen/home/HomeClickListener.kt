package com.kc_hsu.podcastlite.screen.home

import com.kc_hsu.podcastlite.data.local.BestPodcastModel

interface PodcastClickListener {
    fun onPodcastClick(podcast: BestPodcastModel)
}

interface SettingClickListener {
    fun onSettingClick()
}
