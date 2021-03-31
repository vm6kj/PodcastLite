package com.kc_hsu.podcastlite.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.carousel
import com.kc_hsu.podcastlite.BestPodcastBindingModel_
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.bestPodcast
import com.kc_hsu.podcastlite.title
import com.kc_hsu.podcastlite.utils.TestUtils
import kotlinx.android.synthetic.main.home_fragment.rv_home

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setupRecycleView()
    }

    // TODO ViewPager2 horizontal scrolling conflict with epoxy carousel https://stackoverflow.com/a/57589708/7871271
    private fun setupRecycleView() {
        rv_home.withModels {
            title {
                id("title_id_1")
                title("KCTEST1")
            }

            val bestPodcastModels =
                TestUtils.getTestPodcastList(requireActivity()).map { currentPodcast ->
                    BestPodcastBindingModel_()
                        .id(currentPodcast.id)
                        .podcast(currentPodcast)
                        .clickListener { v ->
                            Toast.makeText(
                                requireContext(),
                                "${currentPodcast.title} clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

            carousel {
                id("best_podcast")
                models(bestPodcastModels)
            }

            title {
                id("title_id_2")
                title("KCTEST2")
            }

            TestUtils.getTestPodcastList(requireActivity()).forEach { currentPodcast ->
                bestPodcast {
                    id(currentPodcast.id)
                    podcast(currentPodcast)
                    clickListener { v ->
                        Toast.makeText(
                            requireContext(),
                            "${currentPodcast.title} clicked in vertical list!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
