package com.kc_hsu.podcastlite.ui.podcastlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.utils.DividerItemDecorator
import kotlinx.android.synthetic.main.podcast_list_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class PodcastListFragment : Fragment() {

    private val podcastListViewModel: PodcastListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val podcastListAdapter by inject<PodcastListAdapter> { parametersOf(podcastListViewModel) }

        rv_podcast_list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = podcastListAdapter

            addItemDecoration(
                DividerItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
                    resources.getInteger(R.integer.photo_list_preview_columns)
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            podcastListViewModel.flow.collectLatest {
                podcastListAdapter.submitData(it)
            }
        }

        podcastListViewModel.clickedPodcast.observe(viewLifecycleOwner, Observer { event ->
            val podcast = event.getContentIfNotHandled()
            podcast?.apply {
                if (podcast.id == "160904630") {
                    val action =
                        PodcastListFragmentDirections.actionPodcastListFragmentToPodcastDetailFragment()
                    findNavController().navigate(action)
                } else {
                    Timber.d("Mock API doesn't provide ${podcast.name}'s detail data except TED Talks Daily")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_podcast_list.adapter = null
    }
}