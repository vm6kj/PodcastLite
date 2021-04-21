package com.kc_hsu.podcastlite.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.utils.Listing
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors

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

    fun getBestPodcastList(): Listing<BestPodcastsBody.Podcast> {
        val bestPodcastDataSourceFactory: BestPodcastDataSourceFactory by inject()
        val pagedList: LiveData<PagedList<BestPodcastsBody.Podcast>> by lazy {
            val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(5)
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
}
