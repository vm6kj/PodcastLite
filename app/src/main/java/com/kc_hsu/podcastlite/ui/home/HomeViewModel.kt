package com.kc_hsu.podcastlite.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class HomeViewModel : ViewModel(), KoinComponent {

    private var _bestPodcasts = MutableLiveData<BestPodcastsBody>()
    val bestPodcasts: LiveData<BestPodcastsBody>
        get() = _bestPodcasts

    fun getBestPodcastList(genreId: Int): Listing<BestPodcastsBody.Podcast> {
        return PodcastRepo.getBestPodcastList(genreId)
    }

    fun getBestPodcasts(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _bestPodcasts.postValue(PodcastRepo.getBestPodcasts(genreId))
        }
    }
}
