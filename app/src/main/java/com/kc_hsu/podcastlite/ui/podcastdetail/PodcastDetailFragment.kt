package com.kc_hsu.podcastlite.ui.podcastdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.databinding.PodcastDetailFragmentBinding
import kotlinx.android.synthetic.main.podcast_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PodcastDetailFragment : Fragment() {

    // Avoiding the memory leak, refer to https://proandroiddev.com/avoiding-memory-leaks-when-using-data-binding-and-view-binding-3b91d571c150
    private var _binding: PodcastDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PodcastDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val podcastDetailViewModel: PodcastDetailViewModel by viewModel()
        val podcastDetailAdapter by inject<PodcastDetailAdapter> {
            parametersOf(
                podcastDetailViewModel
            )
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
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
                val action =
                    PodcastDetailFragmentDirections.actionPodcastDetailFragmentToPodcastPlayerFragment(
                        title,
                        contentUrl
                    )
                findNavController().navigate(action)
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
        _binding = null
    }
}