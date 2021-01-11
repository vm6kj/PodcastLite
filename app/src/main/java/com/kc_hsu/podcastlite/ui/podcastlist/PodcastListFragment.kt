package com.kc_hsu.podcastlite.ui.podcastlist

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.AutoClearedValue
import com.kc_hsu.podcastlite.base.BaseBindingFragment
import com.kc_hsu.podcastlite.databinding.PodcastListFragmentBinding
import com.kc_hsu.podcastlite.utils.DividerItemDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class PodcastListFragment(override val viewModel: PodcastListViewModel) :
    BaseBindingFragment<PodcastListFragmentBinding, PodcastListViewModel>(R.layout.podcast_list_fragment) {

    private var podcastListAdapter by AutoClearedValue<PodcastListAdapter>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        podcastListAdapter = get { parametersOf(viewModel) }

        binding.rvPodcastList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = podcastListAdapter

            addItemDecoration(
                DividerItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
                    resources.getInteger(R.integer.photo_list_preview_columns)
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.flow.collectLatest {
                podcastListAdapter.submitData(it)
            }
        }

        viewModel.clickedPodcast.observe(
            viewLifecycleOwner,
            Observer { event ->
                val podcast = event.getContentIfNotHandled()
                podcast?.apply {
                    if (podcast.id == "160904630") {
                        val action =
                            PodcastListFragmentDirections.actionPodcastListFragmentToPodcastDetailFragment()
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(
                            context,
                            "Mock API doesn't provide <${podcast.name}>'s detail data except TED Talks Daily",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }
}
