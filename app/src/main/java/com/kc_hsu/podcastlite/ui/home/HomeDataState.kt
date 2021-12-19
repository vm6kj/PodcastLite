package com.kc_hsu.podcastlite.ui.home

import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody

sealed class HomeDataState {
    object Idle : HomeDataState()
    object Loading : HomeDataState()
    data class Success(val data: BestPodcastsBody) : HomeDataState()
    data class Error<T>(val error: T? = null) : HomeDataState()
}
