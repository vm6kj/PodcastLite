package com.kc_hsu.podcastlite.ui.podcastplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kc_hsu.podcastlite.R
import kotlinx.android.synthetic.main.custom_player_control_view.*
import kotlinx.android.synthetic.main.podcast_player_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PodcastPlayerFragment : Fragment() {

    private val podcastPlayerViewModel: PodcastPlayerViewModel by viewModel()
    private val args: PodcastPlayerFragmentArgs by navArgs()

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

        player_control_view.player = podcastPlayerViewModel.initMp3Player(mp3Url)
        player_control_view.show()
    }

    override fun onStop() {
        super.onStop()
        Timber.d("KCTEST onStop()")
        podcastPlayerViewModel.releaseMp3Player()
    }
}