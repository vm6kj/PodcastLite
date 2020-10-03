package com.kc_hsu.podcastlite.ui.podcastlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kc_hsu.podcastlite.Event
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody

class PodcastListViewModel : ViewModel() {

    val clickedPodcast = MutableLiveData<Event<PodcastBody.Data.Podcast>>()

    val flow = Pager(PagingConfig(pageSize = 10)) {
        PodcastListPagingSource()
    }.flow.cachedIn(viewModelScope)

    fun onPodcastClicked(podcast: PodcastBody.Data.Podcast) {
        clickedPodcast.value = Event(podcast)
    }
}