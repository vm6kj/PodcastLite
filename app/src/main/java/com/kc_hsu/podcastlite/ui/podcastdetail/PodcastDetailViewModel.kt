package com.kc_hsu.podcastlite.ui.podcastdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kc_hsu.podcastlite.Event
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody

class PodcastDetailViewModel : ViewModel() {

    val clieckedEpisode = MutableLiveData<Event<PodcastDetailBody.Data.Collection.ContentFeed>>()
    val podcastCoverData = MutableLiveData<PodcastCoverData>()

    val flow = Pager(PagingConfig(pageSize = 10)) {
        PodcastDetailPagingSource(podcastCoverData)
    }.flow.cachedIn(viewModelScope)

    fun onEpisodeClicked(podcastfeed: PodcastDetailBody.Data.Collection.ContentFeed) {
        clieckedEpisode.value = Event(podcastfeed)
    }
}
