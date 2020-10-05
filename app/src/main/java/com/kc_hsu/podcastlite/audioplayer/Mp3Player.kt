package com.kc_hsu.podcastlite.audioplayer

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class Mp3Player(private val context: Context) {

    companion object {

    }

    fun getPlayer() {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context)
        val drmSessionManager = DrmSessionManager.getDummyDrmSessionManager()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .setDrmSessionManager(drmSessionManager)
            .createMediaSource(MediaItem.fromUri("https://dts.podtrac.com/redirect.mp3/download.ted.com/talks/MattCutts_2019U.mp3?apikey=172BB350-0207&prx_url=https://dovetail.prxu.org/70/1b56e1b3-9eaa-4918-a9a3-f69650636d5c/MattCutts_2019U_VO_Intro.mp3")) // TODO put the mp3 uri here

        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
        player.setMediaSource(mediaSource)
        player.prepare()
        player.play()
        player.repeatMode = Player.REPEAT_MODE_OFF
    }
}