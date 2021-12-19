package com.kc_hsu.podcastlite.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber

class HomeViewModel : ViewModel(), KoinComponent {

    private var _bestPodcasts = MutableLiveData<BestPodcastsBody>()
    val bestPodcasts: LiveData<BestPodcastsBody>
        get() = _bestPodcasts

    private var _homeState = MutableStateFlow<HomeDataState>(HomeDataState.Idle)
    val homeState: StateFlow<HomeDataState>
        get() = _homeState

    fun getBestPodcastList(genreId: Int): Listing<BestPodcastsBody.Podcast> {
        return PodcastRepo.getBestPodcastList(genreId)
    }

    fun getBestPodcasts(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _homeState.value = HomeDataState.Loading
            _homeState.value = PodcastRepo.getBestPodcasts(genreId)?.let {
                if (it.podcasts.isNullOrEmpty()) {
                    return@let HomeDataState.Error("Cannot fetch best podcast")
                }
                HomeDataState.Success(it)
            } ?: HomeDataState.Error("Cannot fetch best podcast")
        }
    }
}
