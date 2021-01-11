package com.kc_hsu.podcastlite.data

import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

object PodcastRepo : KoinComponent {

    private val podcastApi: PodcastApi by inject()

    suspend fun getPodcastList(): List<PodcastBody.Data.Podcast>? {
        val response = podcastApi.getCasts()
        return if (response.isSuccessful) {
            response.body()?.data?.podcast
        } else {
            Timber.e("Failed to getCasts(), e: ${response.errorBody()}")
            null
        }
    }

    suspend fun getPodcastDetail(): PodcastDetailBody? {
        val response = podcastApi.getcastdetail()
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.e("Failed to getPodcastDetailList(), e: ${response.errorBody()}")
            null
        }
    }
}
