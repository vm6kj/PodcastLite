package com.kc_hsu.podcastlite.data

import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody

interface PodcastRepo {

    suspend fun getPodcastList(): List<PodcastBody.Data.Podcast>?
    suspend fun getPodcastDetail(): PodcastDetailBody?
    suspend fun getPodcastEpisode(podcastId: String, nextEpisodePubDate: Long?): PodcastsBody?
    suspend fun getBestPodcasts(genreId: Int): List<BestPodcastModel>?
}
