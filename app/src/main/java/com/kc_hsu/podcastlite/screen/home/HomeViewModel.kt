package com.kc_hsu.podcastlite.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow<HomeDataState>(HomeDataState.Idle)
    val homeState = _homeState.asStateFlow()

    fun getBestPodcasts() {
        viewModelScope.launch(Dispatchers.IO) {
            val bestPodcastsBodies = arrayListOf<List<BestPodcastModel>>()
            PodcastGenres.values().forEachIndexed { index, podcastGenres ->
                _homeState.update { HomeDataState.Loading }
                _homeState.update {
                    PodcastRepo.getBestPodcasts(podcastGenres.genreId)?.let {
                        if (it.isNullOrEmpty()) {
                            return@let HomeDataState.Error("Cannot fetch best podcast")
                        }
                        // TODO Remove shuffle before release
                        bestPodcastsBodies.add(it.shuffled())
                        return@let HomeDataState.Success(bestPodcastsBodies)
                    } ?: HomeDataState.Error("Cannot fetch best podcast")
                }
            }
        }
    }
}
