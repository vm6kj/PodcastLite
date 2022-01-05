package com.kc_hsu.podcastlite.screen.home

import com.kc_hsu.podcastlite.base.BaseViewModel
import com.kc_hsu.podcastlite.data.PodcastRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.inject

class HomeViewModel : BaseViewModel<HomeDataState>() {

    private val podcastRepo by inject<PodcastRepo>()

    suspend fun getBestPodcastsByGenre(genreId: Int) {
        // https://proandroiddev.com/no-more-livedata-in-your-repository-there-are-better-options-25a7557b0730
        podcastRepo.getBestPodcastsInFlow(genreId).distinctUntilChanged().collect {
            _viewEvent.tryEmit(it)
        }
    }
}
