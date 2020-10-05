package com.kc_hsu.podcastlite.ui.podcastplayer

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class PodcastPlayerViewModel : ViewModel(), KoinComponent {

    private var player: SimpleExoPlayer? = null

    fun initMp3Player(mp3Url: String): SimpleExoPlayer {
        player = get { parametersOf(mp3Url) }
        return player!!
    }

    fun releaseMp3Player() {
        if (player!!.isPlaying) {
            player!!.playWhenReady = false
        }
        player!!.stop()
        player!!.release()
        player = null
    }

}