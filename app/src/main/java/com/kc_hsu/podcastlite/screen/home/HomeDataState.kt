package com.kc_hsu.podcastlite.screen.home

import com.kc_hsu.podcastlite.base.BaseViewEvent
import com.kc_hsu.podcastlite.data.local.BestPodcastModel

sealed class HomeDataState : BaseViewEvent {
    object Idle : HomeDataState()
    object Loading : HomeDataState()
    object Done : HomeDataState()
    data class Success(val data: List<BestPodcastModel>) : HomeDataState()
    data class Error<T>(val error: T? = null) : HomeDataState()
}
