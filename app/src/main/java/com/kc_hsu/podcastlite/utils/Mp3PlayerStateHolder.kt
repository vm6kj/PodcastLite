package com.kc_hsu.podcastlite.utils

data class Mp3PlayerStateHolder(
    var playWhenReady: Boolean = true,
    var currentWindow: Int = 0,
    var playbackPosition: Long = 0
)
