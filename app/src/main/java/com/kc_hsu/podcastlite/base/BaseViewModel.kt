package com.kc_hsu.podcastlite.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.core.KoinComponent

abstract class BaseViewModel<VE : BaseViewEvent> : ViewModel(), KoinComponent {

    protected val _viewEvent = MutableSharedFlow<VE>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val viewEvent = _viewEvent.asSharedFlow()
}
