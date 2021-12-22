package com.kc_hsu.podcastlite.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kc_hsu.podcastlite.data.datasource.BestPodcastDataSourceFactory
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object PodcastRepo : KoinComponent {
    private const val DEFAULT_PAGE_SIZE = 1

    private val api: PodcastApi by inject()

    suspend fun getPodcastList(): List<PodcastBody.Data.Podcast>? {
        val response = api.getCasts()
        return if (response.isSuccessful) {
            response.body()?.data?.podcast
        } else {
            Timber.e("Failed to getCasts(), e: ${response.errorBody()}")
            null
        }
    }

    suspend fun getPodcastDetail(): PodcastDetailBody? {
        val response = api.getcastdetail()
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.e("Failed to getPodcastDetailList(), e: ${response.errorBody()}")
            null
        }
    }

    fun getBestPodcastList(
        genreId: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Listing<BestPodcastsBody.Podcast> {
        val bestPodcastDataSourceFactory: BestPodcastDataSourceFactory by inject {
            parametersOf(
                genreId
            )
        }
        val pagedList: LiveData<PagedList<BestPodcastsBody.Podcast>> by lazy {
            val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .build()
            val executor: Executor = Executors.newFixedThreadPool(5)

            LivePagedListBuilder(bestPodcastDataSourceFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
        }

        return Listing(
            pagedList = pagedList,
            refreshState = bestPodcastDataSourceFactory.initLoadState,
            loadMoreState = bestPodcastDataSourceFactory.loadMoreState,
            refresh = { bestPodcastDataSourceFactory.dataSource?.invalidate() }
        )
    }

    suspend fun getPodcastEpisode(podcastId: String, nextEpisodePubDate: Long?): PodcastsBody? {
        val response = api.podcastsById(podcastId = podcastId, nextEpisodePubDate = nextEpisodePubDate, sort = "recent_first")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getBestPodcasts(genreId: Int): BestPodcastsBody? {
        val response = api.bestPodcasts(genreId = genreId, page = 1, region = "us")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
