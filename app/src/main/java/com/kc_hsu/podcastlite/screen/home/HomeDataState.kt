package com.kc_hsu.podcastlite.screen.home

import com.kc_hsu.podcastlite.data.local.BestPodcastModel

sealed class HomeDataState {
    object Idle : HomeDataState()
    object Loading : HomeDataState()
    data class Success(val data: List<List<BestPodcastModel>>) : HomeDataState()
    data class Error<T>(val error: T? = null) : HomeDataState()
}
