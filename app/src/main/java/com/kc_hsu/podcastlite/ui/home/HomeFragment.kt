package com.kc_hsu.podcastlite.ui.home

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import timber.log.Timber

class HomeFragment : BaseViewBindingFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel = HomeViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadView()
    }

    private fun loadView() {
        PodcastGenres.values().forEachIndexed { index, genre ->
            // val bestPodcastListing = viewModel.getBestPodcastList(genre.genreId)
            if (index == 0) {
                viewModel.getBestPodcasts(genre.genreId)
                viewModel.bestPodcasts.observe(viewLifecycleOwner, {
                    Timber.d("Assign home adapter")
                    val homeAdapter = HomeAdapter(it)
                    binding.rvHome.apply {
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        setHasFixedSize(true)
                        adapter = homeAdapter
                    }
                })
            }
        }
    }
}
