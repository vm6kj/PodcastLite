package com.kc_hsu.podcastlite.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc_hsu.podcastlite.Event
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber

class HomeViewModel : ViewModel(), KoinComponent {

    private val _homeState = MutableStateFlow<HomeDataState>(HomeDataState.Idle)
    val homeState = _homeState.asStateFlow()

    fun getBestPodcasts() {
        viewModelScope.launch(Dispatchers.IO) {
            val bestPodcastsBodys = arrayListOf<BestPodcastsBody>()
            PodcastGenres.values().forEachIndexed { index, podcastGenres ->
                Timber.d("getBestPodcasts: ${podcastGenres.genreId}")
                _homeState.update { HomeDataState.Loading }
                _homeState.update {
                    PodcastRepo.getBestPodcasts(podcastGenres.genreId)?.let {
                        if (it.podcasts.isNullOrEmpty()) {
                            return@let HomeDataState.Error("Cannot fetch best podcast")
                        }
                        // TODO Remove shuffle before release
                        bestPodcastsBodys.add(it.copy(name = "${it.name}_$index", podcasts = it.podcasts?.shuffled()))
                        return@let HomeDataState.Success(bestPodcastsBodys)
                    } ?: HomeDataState.Error("Cannot fetch best podcast")
                }
            }
        }
    }
}
