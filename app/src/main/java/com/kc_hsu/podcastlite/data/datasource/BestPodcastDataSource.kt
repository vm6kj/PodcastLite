package com.kc_hsu.podcastlite.data.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.kc_hsu.podcastlite.data.PodcastApi
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class BestPodcastDataSource(
    private val genreId: Int,
    private val initLoadState: MutableStateFlow<NetworkState>,
    private val loadMoreState: MutableStateFlow<NetworkState>
) : PageKeyedDataSource<Int, BestPodcastsBody.Podcast>(), KoinComponent {

    private val api: PodcastApi by inject()
    private var currentPage: Int = -1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, BestPodcastsBody.Podcast>
    ) {
        currentPage = 1
        GlobalScope.launch(Dispatchers.IO) {
            initLoadState.value = NetworkState.LOADING

            // TODO `region` should be get from global
            api.bestPodcasts(genreId, currentPage, "us", 0).run {
                if (!this.isSuccessful) {
                    initLoadState.value = NetworkState.ERROR
                }

                this.body()?.podcasts?.let {
                    callback.onResult(it, null, calculateNextPage(this.body()?.hasNext!!))
                    initLoadState.value = NetworkState.IDLE
                } ?: let {
                    Timber.e("Failed to fetch best podcast, e=${it.errorBody()}")
                    initLoadState.value = NetworkState.ERROR
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, BestPodcastsBody.Podcast>
    ) {
        // TODO Need it?
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, BestPodcastsBody.Podcast>
    ) {
        if (-1 == params.key || NetworkState.LOADING == loadMoreState.value) {
            Timber.d("No need to loadAfter, params.key=${params.key}, loadMoreState=${loadMoreState.value}")
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            loadMoreState.value = NetworkState.LOADING
            api.bestPodcasts(genreId, params.key, "us", 0).run {
                if (!this.isSuccessful) {
                    loadMoreState.value = NetworkState.ERROR
                }

                this.body()?.podcasts?.let {
                    val nextPage = calculateNextPage(this.body()?.hasNext!!)
                    callback.onResult(it, calculateNextPage(this.body()?.hasNext!!))
                    loadMoreState.value = NetworkState.IDLE
                } ?: let {
                    Timber.e("Failed to fetch best podcast, e=${it.errorBody()}")
                    loadMoreState.value = NetworkState.ERROR
                }
            }
        }
    }

    private fun calculateNextPage(hasNext: Boolean): Int {
        currentPage = if (hasNext) {
            currentPage.plus(1)
        } else {
            -1
        }

        return currentPage
    }
}

class BestPodcastDataSourceFactory(private val genreId: Int) :
    DataSource.Factory<Int, BestPodcastsBody.Podcast>() {

    private val _initLoadState = MutableStateFlow(NetworkState.IDLE)
    val initLoadState: StateFlow<NetworkState> = _initLoadState

    private val _loadMoreState = MutableStateFlow(NetworkState.IDLE)
    val loadMoreState: StateFlow<NetworkState> = _loadMoreState

    var dataSource: BestPodcastDataSource? = null

    override fun create(): DataSource<Int, BestPodcastsBody.Podcast> {
        // TODO KCTEST genreId should be inject from outside
        dataSource = BestPodcastDataSource(genreId, _initLoadState, _loadMoreState)
        return dataSource!!
    }
}
