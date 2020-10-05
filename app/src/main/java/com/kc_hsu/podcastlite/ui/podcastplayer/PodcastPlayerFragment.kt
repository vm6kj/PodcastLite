package com.kc_hsu.podcastlite.ui.podcastplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kc_hsu.podcastlite.R
import kotlinx.android.synthetic.main.custom_player_control_view.*
import kotlinx.android.synthetic.main.podcast_player_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PodcastPlayerFragment : Fragment() {

    private val podcastPlayerViewModel: PodcastPlayerViewModel by viewModel()
    private val args: PodcastPlayerFragmentArgs by navArgs()

    private var player: SimpleExoPlayer?= null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }

            })
    }

    override fun onResume() {
        super.onResume()
        Timber.d("KCTEST onResume()")
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
        Timber.d("KCTEST onStop()")
        releasePlayer()
    }

    private fun initPlayer(url: String) {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        val mediaItem = MediaItem.fromUri(url)
        player!!.setMediaItem(mediaItem)
        player_control_view.player = player
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()
        player_control_view.show()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }
}