package com.kc_hsu.podcastlite.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber

class HomeViewModel : ViewModel(), KoinComponent {

    private var _bestPodcasts = MutableLiveData<BestPodcastsBody>()
    val bestPodcasts: LiveData<BestPodcastsBody>
        get() = _bestPodcasts

    private var _homeState = MutableStateFlow<HomeDataState>(HomeDataState.Idle)
    val homeState = _homeState.asStateFlow()

    fun getBestPodcastList(genreId: Int): Listing<BestPodcastsBody.Podcast> {
        return PodcastRepo.getBestPodcastList(genreId)
    }

    fun getBestPodcasts() {
        viewModelScope.launch(Dispatchers.IO) {
            PodcastGenres.values().forEachIndexed { index, podcastGenres ->
                Timber.d("getBestPodcasts: ${podcastGenres.genreId}")
                // _homeState.update { HomeDataState.Loading }
                _homeState.update {
                    PodcastRepo.getBestPodcasts(podcastGenres.genreId)?.let {
                        Timber.d("do i get it?: ${!it.podcasts.isNullOrEmpty()}")
                        if (it.podcasts.isNullOrEmpty()) {
                            return@let HomeDataState.Error("Cannot fetch best podcast")
                        }
                        // TODO Remove id change before release
                        HomeDataState.Success(it.copy(id = podcastGenres.genreId))
                    } ?: HomeDataState.Error("Cannot fetch best podcast")
                }
            }
        }
    }
}
