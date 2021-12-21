package com.kc_hsu.podcastlite.screen.podcastdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.databinding.PodcastDetailFragmentBinding
import com.kc_hsu.podcastlite.screen.home.HomeViewModel

class PodcastDetailFragment : BaseViewBindingFragment<PodcastDetailFragmentBinding>(PodcastDetailFragmentBinding::inflate) {

    companion object {
        fun newInstance(): PodcastDetailFragment {
            val args = Bundle()

            val fragment = PodcastDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
