package com.kc_hsu.podcastlite.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.BaseBindingFragment
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import com.kc_hsu.podcastlite.ui.home.controller.BestPodcastController
import com.kc_hsu.podcastlite.ui.home.controller.HomeController
import com.kc_hsu.podcastlite.utils.NetworkState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber

class HomeFragment :
    BaseBindingFragment<HomeFragmentBinding, HomeViewModel>(R.layout.home_fragment), KoinComponent {

    override val viewModel: HomeViewModel by viewModel()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeController = HomeController(requireContext())
        val podcastGenreListings = mutableMapOf<PodcastGenres, PodcastGenreListing>()
        PodcastGenres.values().forEachIndexed { index, genre ->
            val itemsOnScreen: Float = if (index == 0) {
                1.2f
            } else {
                2.1f
            }
            val bestPodcastController = BestPodcastController()

            val bestPodcastListing = viewModel.getBestPodcastList(genre.genreId)
            bestPodcastListing.pagedList.observe(
                viewLifecycleOwner,
                Observer {
                    bestPodcastController.submitList(it)
                    bestPodcastController.requestModelBuild()
                }
            )

            // Unused by now
            lifecycleScope.launchWhenResumed {
                bestPodcastListing.loadMoreState.collectLatest {
                    Timber.d("loadMoreState=$it")
                    bestPodcastController.loadMore = (it == NetworkState.LOADING)
                }
            }

            podcastGenreListings[genre] = PodcastGenreListing(
                NetworkState.LOADING,
                bestPodcastController,
                itemsOnScreen
            )
        }

        homeController.podcastGenreListings = podcastGenreListings

        with(binding.rvHome) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setControllerAndBuildModels(homeController)
        }
    }
}
