package com.kc_hsu.podcastlite.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.BaseBindingFragment
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import com.kc_hsu.podcastlite.ui.home.controller.BestPodcastController
import com.kc_hsu.podcastlite.ui.home.controller.HomeController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber

class HomeFragment : BaseBindingFragment<HomeFragmentBinding, HomeViewModel>(R.layout.home_fragment), KoinComponent {

    override val viewModel: HomeViewModel by viewModel()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // setupRecycleView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bestCarouselController = BestPodcastController()

//         lifecycleScope.launchWhenResumed {
//             dataSourceFactory.loadMoreState.collectLatest {
//                 Timber.d("loadMoreState=${it}")
//                 bestCarouselController.loadMore = (it == NetworkState.LOADING)
//             }
//         }

        val bestPodcastListing = viewModel.getBestPodcastList()
        bestPodcastListing.pagedList.observe(
            viewLifecycleOwner,
            Observer {
                Timber.d("pagedList update! ${it.size}")
                bestCarouselController.submitList(it)
                bestCarouselController.requestModelBuild()
            }
        )

        val homeController = HomeController()
        homeController.controller = bestCarouselController

        with(binding.rvHome) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setControllerAndBuildModels(homeController)
        }
    }

    // TODO ViewPager2 horizontal scrolling conflict with epoxy carousel https://stackoverflow.com/a/57589708/7871271
    // private fun setupRecycleView() {
    //     rv_home.withModels {
    //         title {
    //             id("title_id_1")
    //             title("KCTEST1")
    //         }
    //
    //         val bestPodcastModels =
    //             TestUtils.getTestPodcastList(requireActivity()).map { currentPodcast ->
    //                 BestPodcastBindingModel_()
    //                     .id(currentPodcast.id)
    //                     .podcast(currentPodcast)
    //                     .clickListener { v ->
    //                         Toast.makeText(
    //                             requireContext(),
    //                             "${currentPodcast.title} clicked!",
    //                             Toast.LENGTH_SHORT
    //                         ).show()
    //                     }
    //             }
    //
    //         carousel {
    //             id("best_podcast")
    //             models(bestPodcastModels)
    //             numViewsToShowOnScreen(1.1F)
    //         }
    //
    //         title {
    //             id("title_id_2")
    //             title("KCTEST2")
    //         }
    //
    //         TestUtils.getTestPodcastList(requireActivity()).forEach { currentPodcast ->
    //             bestPodcast {
    //                 id(currentPodcast.id)
    //                 podcast(currentPodcast)
    //                 clickListener { v ->
    //                     Toast.makeText(
    //                         requireContext(),
    //                         "${currentPodcast.title} clicked in vertical list!",
    //                         Toast.LENGTH_SHORT
    //                     ).show()
    //                 }
    //             }
    //         }
    //     }
    // }
}
