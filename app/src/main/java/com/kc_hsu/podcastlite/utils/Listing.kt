package com.kc_hsu.podcastlite.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import kotlinx.coroutines.flow.StateFlow

data class Listing<T : Any>(
    val pagedList: LiveData<PagedList<T>>,
    val refreshState: StateFlow<NetworkState>,
    val loadMoreState: StateFlow<NetworkState>,
    val refresh: () -> Unit = {}
)
