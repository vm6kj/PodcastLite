package com.kc_hsu.podcastlite.data

import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import com.kc_hsu.podcastlite.screen.home.HomeDataState
import kotlinx.coroutines.flow.Flow

interface PodcastRepo {

    suspend fun getPodcastList(): List<PodcastBody.Data.Podcast>?
    suspend fun getPodcastDetail(): PodcastDetailBody?
    suspend fun getPodcastEpisode(podcastId: String, nextEpisodePubDate: Long?): PodcastsBody?
    suspend fun getBestPodcasts(genreId: Int): List<BestPodcastModel>?
    suspend fun getBestPodcastsInFlow(genreId: Int): Flow<HomeDataState>
}
