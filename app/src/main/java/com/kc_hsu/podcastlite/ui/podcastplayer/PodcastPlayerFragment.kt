package com.kc_hsu.podcastlite.ui.podcastplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.utils.Mp3PlayerStateHolder
import kotlinx.android.synthetic.main.custom_player_control_view.*
import kotlinx.android.synthetic.main.custom_player_control_view.view.*
import kotlinx.android.synthetic.main.podcast_player_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PodcastPlayerFragment : Fragment() {

    private val podcastPlayerViewModel: PodcastPlayerViewModel by viewModel()
    private val args: PodcastPlayerFragmentArgs by navArgs()
    private var mp3PlayerStateHolder = Mp3PlayerStateHolder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        podcastPlayerViewModel.artwork.observe(
            viewLifecycleOwner,
            Observer {
                player_control_view.iv_cover.setImageBitmap(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        val title = args.title
        val mp3Url = args.mp3Url

        if (title.isNullOrBlank()) {
            Timber.e("title is null")
            return
        }
        if (mp3Url.isNullOrBlank()) {
            Timber.e("mp3Url is null")
            return
        }
        tv_episode_name.text = title

        initPlayer(mp3Url)
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initPlayer(url: String) {
        val player = podcastPlayerViewModel.initMp3Player(url, mp3PlayerStateHolder)
        player_control_view.player = player
        player_control_view.show()
    }

    private fun releasePlayer() {
        mp3PlayerStateHolder = podcastPlayerViewModel.releaseMp3Player()
    }
}
