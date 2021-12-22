package com.kc_hsu.podcastlite.screen.podcastdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.PodcastDetailFragmentBinding
import com.kc_hsu.podcastlite.screen.home.HomeViewModel

class PodcastDetailFragment :
    BaseViewBindingFragment<PodcastDetailFragmentBinding>(PodcastDetailFragmentBinding::inflate) {

    companion object {
        private const val KEY_PODCAST = "podcast"

        fun newInstance(podcast: BestPodcastsBody.Podcast): PodcastDetailFragment {
            val args = Bundle()
            args.putSerializable(KEY_PODCAST, podcast)

            val fragment = PodcastDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: HomeViewModel by activityViewModels()
    private val podcast: BestPodcastsBody.Podcast by lazy {
        requireArguments().getSerializable(KEY_PODCAST) as BestPodcastsBody.Podcast
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        with(binding) {
            // top-left image view
            Glide.with(requireView())
                .load(podcast.image)
                .placeholder(R.drawable.ic_mic_none)
                .error(R.drawable.ic_mic_none)
                .fitCenter()
                .into(ivCover)

            tvPodcastName.text = podcast.title
            tvPodcastDescription.text = podcast.description
        }
    }
}
