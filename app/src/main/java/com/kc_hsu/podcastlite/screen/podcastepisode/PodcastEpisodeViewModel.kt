package com.kc_hsu.podcastlite.screen.podcastepisode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kc_hsu.podcastlite.data.datasource.EpisodeDataSource
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import kotlinx.coroutines.flow.Flow

class PodcastEpisodeViewModel : ViewModel() {

    lateinit var episodes: Flow<PagingData<PodcastsBody.Episode>>
    fun getEpisodeWithFlow(podcastId: String) {
        // TODO Use combineTransform() to combine loading state.
        episodes = Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { EpisodeDataSource(podcastId) }
        ).flow.cachedIn(viewModelScope)
    }
}
