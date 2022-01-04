package com.kc_hsu.podcastlite.screen.podcastepisode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.databinding.PodcastEpisodeFragmentBinding
import kotlinx.coroutines.flow.collectLatest

class PodcastEpisodeFragment :
    BaseViewBindingFragment<PodcastEpisodeFragmentBinding>(PodcastEpisodeFragmentBinding::inflate) {

    companion object {
        private const val KEY_PODCAST = "podcast"

        fun newInstance(podcast: BestPodcastModel): PodcastEpisodeFragment {
            val args = Bundle()
            args.putSerializable(KEY_PODCAST, podcast)

            val fragment = PodcastEpisodeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: PodcastEpisodeViewModel by viewModels()
    private val podcast: BestPodcastModel by lazy {
        requireArguments().getSerializable(KEY_PODCAST) as BestPodcastModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        viewModel.getEpisodeWithFlow(podcast.id)
        val podcastEpisodeAdapter = PodcastEpisodeAdapter()
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

            rvPodcastDetailList.adapter = podcastEpisodeAdapter
            rvPodcastDetailList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            rvPodcastDetailList.addItemDecoration(divider)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.episodes.collectLatest { pagingData ->
                podcastEpisodeAdapter.submitData(pagingData)
            }
        }
    }
}
