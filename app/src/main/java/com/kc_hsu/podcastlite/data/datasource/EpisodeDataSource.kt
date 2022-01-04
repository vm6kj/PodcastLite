package com.kc_hsu.podcastlite.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import org.koin.core.KoinComponent
import org.koin.core.inject

class EpisodeDataSource(private val podcastId: String) :
    PagingSource<Long, PodcastsBody.Episode>(), KoinComponent {

    private val podcastRepo by inject<PodcastRepo>()

    override fun getRefreshKey(state: PagingState<Long, PodcastsBody.Episode>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, PodcastsBody.Episode> {
        // Allow null cuz the default page key of podcasts/{id} API would be null
        val page: Long? = params.key
        val podcastsBody = podcastRepo.getPodcastEpisode(podcastId, page)
        if (page == podcastsBody?.nextEpisodePubDate) {
            return LoadResult.Error(IllegalStateException("there is no more key"))
        }
        return try {
            LoadResult.Page(
                data = podcastsBody?.episodes.orEmpty(),
                prevKey = null,
                nextKey = podcastsBody?.nextEpisodePubDate
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
