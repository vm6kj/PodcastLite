package com.kc_hsu.podcastlite.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kc_hsu.podcastlite.data.datasource.BestPodcastDataSourceFactory
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.data.local.PodcastDao
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import retrofit2.HttpException
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object PodcastRepo : KoinComponent {
    private const val DEFAULT_PAGE_SIZE = 1

    private val api: PodcastApi by inject()
    private val podcastDao: PodcastDao by inject()

    @SuppressWarnings("unused")
    suspend fun getPodcastList(): List<PodcastBody.Data.Podcast>? {
        val response = api.getCasts()
        return if (response.isSuccessful) {
            response.body()?.data?.podcast
        } else {
            Timber.e("Failed to getCasts(), e: ${response.errorBody()}")
            null
        }
    }

    @SuppressWarnings("unused")
    suspend fun getPodcastDetail(): PodcastDetailBody? {
        val response = api.getcastdetail()
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.e("Failed to getPodcastDetailList(), e: ${response.errorBody()}")
            null
        }
    }

    @SuppressWarnings("unused")
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
        try {
            val response = api.podcastsById(
                podcastId = podcastId,
                nextEpisodePubDate = nextEpisodePubDate,
                sort = "recent_first"
            )
            return if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: HttpException) {
            Timber.e("HttpException: ${e.message()}")
            return null
        }
    }

    suspend fun getBestPodcasts(genreId: Int): List<BestPodcastModel>? {
        try {
            val response = api.bestPodcasts(genreId = genreId, page = 1, region = "us")

            if (response.isSuccessful) {
                val body = response.body()
                val bestPodcastModels: List<BestPodcastModel> = body?.podcasts?.map {
                    BestPodcastModel(
                        id = it.id!!,
                        genreId = body.id!!,
                        genre = body.name!!,
                        description = it.description,
                        earliestPubDateMs = it.earliestPubDateMs,
                        email = it.email,
                        explicitContent = it.explicitContent,
                        image = it.image,
                        isClaimed = it.isClaimed,
                        itunesId = it.itunesId,
                        language = it.language,
                        latestPubDateMs = it.latestPubDateMs,
                        listenScore = it.listenScore,
                        publisher = it.publisher,
                        thumbnail = it.thumbnail,
                        title = it.title
                    )
                } ?: return null

                podcastDao.upsertBestPodcastList(genreId = body.id!!, bestPodcastModels)

                return bestPodcastModels
            } else {
                return podcastDao.queryBestPodcastList(genreId)
            }
        } catch (e: HttpException) {
            Timber.e("HttpException: ${e.message()}")
            return null
        }
    }
}
