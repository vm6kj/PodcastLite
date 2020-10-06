package com.kc_hsu.podcastlite.ui.podcastdetail

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.AutoClearedValue
import com.kc_hsu.podcastlite.base.BaseBindingFragment
import com.kc_hsu.podcastlite.databinding.PodcastDetailFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class PodcastDetailFragment(override val viewModel: PodcastDetailViewModel) :
    BaseBindingFragment<PodcastDetailFragmentBinding, PodcastDetailViewModel>(R.layout.podcast_detail_fragment) {

    private var podcastDetailAdapter by AutoClearedValue<PodcastDetailAdapter>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        podcastDetailAdapter = get { parametersOf(viewModel) }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.flow.collectLatest {
                podcastDetailAdapter.submitData(it)
            }
        }

        viewModel.podcastCoverData.observe(viewLifecycleOwner, Observer {
            binding.imageurl = it.imageUrl
            binding.collectioname = it.collectionName
            binding.artistname = it.artistName
        })

        viewModel.clieckedEpisode.observe(viewLifecycleOwner, Observer { event ->
            val episode = event.getContentIfNotHandled()
            episode?.apply {
                val action =
                    PodcastDetailFragmentDirections.actionPodcastDetailFragmentToPodcastPlayerFragment(
                        title,
                        contentUrl
                    )
                findNavController().navigate(action)
            }
        })

        binding.rvPodcastDetailList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = podcastDetailAdapter
            val dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.podcast_detail_list_divider
                )!!
            )
            addItemDecoration(dividerItemDecoration)
        }
    }
}