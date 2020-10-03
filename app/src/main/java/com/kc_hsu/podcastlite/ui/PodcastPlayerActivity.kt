package com.kc_hsu.podcastlite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kc_hsu.podcastlite.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_podcast_player.*
import kotlinx.android.synthetic.main.custom_player_control_view.view.*


class PodcastPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast_player)

    }

    override fun onResume() {
        super.onResume()
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this)
        val drmSessionManager = DrmSessionManager.getDummyDrmSessionManager()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .setDrmSessionManager(drmSessionManager)
            .createMediaSource(MediaItem.fromUri("https://dts.podtrac.com/redirect.mp3/download.ted.com/talks/MattCutts_2019U.mp3?apikey=172BB350-0207&prx_url=https://dovetail.prxu.org/70/1b56e1b3-9eaa-4918-a9a3-f69650636d5c/MattCutts_2019U_VO_Intro.mp3")) // TODO put the mp3 uri here

        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(applicationContext).build()
        player.setMediaSource(mediaSource)
        player.prepare()
        player.play()
        player.repeatMode = Player.REPEAT_MODE_ALL

        player_control_view.iv_cover.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_play_circle_outline_24))
        player_control_view.player = player
        player_control_view.show()
    }
}