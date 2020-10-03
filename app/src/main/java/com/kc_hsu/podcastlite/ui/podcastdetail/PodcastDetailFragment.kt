package com.kc_hsu.podcastlite.ui.podcastdetail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.databinding.PodcastDetailFragmentBinding
import com.kc_hsu.podcastlite.ui.BaseBindingFragment
import kotlinx.android.synthetic.main.podcast_detail_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PodcastDetailFragment : BaseBindingFragment<PodcastDetailFragmentBinding>() {

    private val podcastDetailViewModel: PodcastDetailViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.podcast_detail_fragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val podcastDetailAdapter by inject<PodcastDetailAdapter> {
            parametersOf(
                podcastDetailViewModel
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            podcastDetailViewModel.flow.collectLatest {
                podcastDetailAdapter.submitData(it)
            }
        }

        podcastDetailViewModel.podcastCoverData.observe(viewLifecycleOwner, Observer {
            binding.imageurl = it.imageUrl
            binding.collectioname = it.collectionName
            binding.artistname = it.artistName
        })

        podcastDetailViewModel.clieckedEpisode.observe(viewLifecycleOwner, Observer { event ->
            val episode = event.getContentIfNotHandled()
            episode?.apply {
                this.contentUrl
                // TODO QQQQ
            }
        })

        rv_podcast_detail_list.apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
        rv_podcast_detail_list.adapter = null
    }
}