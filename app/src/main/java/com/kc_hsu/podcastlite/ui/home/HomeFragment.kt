package com.kc_hsu.podcastlite.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : BaseViewBindingFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel = HomeViewModel()
    val homeAdapter = HomeAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadView()
    }

    private fun loadView() {
        with(binding.rvHome) {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        val list = ArrayList<BestPodcastsBody>()
        PodcastGenres.values().forEachIndexed { index, genre ->
            // val bestPodcastListing = viewModel.getBestPodcastList(genre.genreId)
            viewModel.getBestPodcasts(genre.genreId)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.homeState
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        when (it) {
                            is HomeDataState.Idle -> {

                            }
                            is HomeDataState.Loading -> {

                            }
                            is HomeDataState.Success -> {
                                // TODO problems here!!!
                                Timber.d("HomeDataState.Success")
//                                homeAdapter.updateData(it.data)
                                list.add(it.data)
                                Timber.d("homeAdapter.updateData(list)")
                                homeAdapter.updateData(list)
                                homeAdapter.notifyDataSetChanged()
                            }
                            is HomeDataState.Error<*> -> {

                            }
                        }
                    }
            }
        }
    }
}
