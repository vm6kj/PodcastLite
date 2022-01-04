package com.kc_hsu.podcastlite.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc_hsu.podcastlite.data.PodcastRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class HomeViewModel : ViewModel(), KoinComponent {

    private val podcastRepo by inject<PodcastRepo>()

    private val _homeStateLiveData = MutableLiveData<HomeDataState>(HomeDataState.Idle)
    val homeStateLiveData = _homeStateLiveData

    init {
        viewModelScope.launch {
            PodcastGenres.values().forEach {
                getBestPodcastsByGenre(it.genreId)
            }
        }
    }

    private suspend fun getBestPodcastsByGenre(genreId: Int) {
        // https://proandroiddev.com/no-more-livedata-in-your-repository-there-are-better-options-25a7557b0730
        podcastRepo.getBestPodcastsInFlow(genreId).distinctUntilChanged().collect {
            Timber.w("getBestPodcastsInFlow collect: $genreId")
            _homeStateLiveData.postValue(it)
        }
    }
}
