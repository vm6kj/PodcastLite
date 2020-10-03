package com.kc_hsu.podcastlite.ui.podcastplayer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kc_hsu.podcastlite.R

class PodcastPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = PodcastPlayerFragment()
    }

    private lateinit var viewModel: PodcastPlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PodcastPlayerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}