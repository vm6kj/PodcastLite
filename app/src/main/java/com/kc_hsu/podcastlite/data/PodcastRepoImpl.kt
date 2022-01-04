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
import com.kc_hsu.podcastlite.screen.home.HomeDataState
import com.kc_hsu.podcastlite.screen.home.PodcastGenres
import com.kc_hsu.podcastlite.utils.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class PodcastRepoImpl(private val api: PodcastApi, private val podcastDao: PodcastDao) : PodcastRepo, KoinComponent {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 1
    }

    @SuppressWarnings("unused")
    override suspend fun getPodcastList(): List<PodcastBody.Data.Podcast>? {
        val response = api.getCasts()
        return if (response.isSuccessful) {
            response.body()?.data?.podcast
        } else {
            Timber.e("Failed to getCasts(), e: ${response.errorBody()}")
            null
        }
    }

    @SuppressWarnings("unused")
    override suspend fun getPodcastDetail(): PodcastDetailBody? {
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

    override suspend fun getPodcastEpisode(podcastId: String, nextEpisodePubDate: Long?): PodcastsBody? {
        return try {
            val response = api.podcastsById(
                podcastId = podcastId,
                nextEpisodePubDate = nextEpisodePubDate,
                sort = "recent_first"
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: HttpException) {
            Timber.e("HttpException: ${e.message()}")
            null
        }
    }

    @SuppressWarnings("unused")
    override suspend fun getBestPodcasts(genreId: Int): List<BestPodcastModel>? {
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

    override suspend fun getBestPodcastsInFlow(genreId: Int): Flow<HomeDataState> {
        return flow {
            try {
                // query from DB cached
                emit(HomeDataState.Loading)
                val test = podcastDao.queryBestPodcastList(genreId)
                if (test.isNotEmpty()) {
                    emit(HomeDataState.Success(test))
                }
                val response = api.bestPodcasts(genreId = genreId, page = 1, region = "us")
                if (response.isSuccessful) {
                    val body = response.body()
                    val bestPodcastModels: List<BestPodcastModel>? = body?.podcasts?.map {
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
                    }

                    bestPodcastModels?.let {
                        podcastDao.upsertBestPodcastList(genreId = genreId, bestPodcastModels)
                        emit(HomeDataState.Success(bestPodcastModels))
                    } ?: emit(HomeDataState.Error("Failed to fetch best podcast from remote"))
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        Timber.e("HttpException: ${e.message}")
                    }
                    is UnknownHostException -> {
                        Timber.e("UnknownHostException: ${e.message}")
                    }
                    else -> {
                        Timber.e("Exception: ${e.message}")
                        emit(HomeDataState.Error("Exception occurred when fetching data"))
                    }
                }
            } finally {
                if (PodcastGenres.FICTION.genreId == genreId) {
                    kotlinx.coroutines.delay(150)
                    emit(HomeDataState.Done)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}
