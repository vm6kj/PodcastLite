package com.kc_hsu.podcastlite.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import com.kc_hsu.podcastlite.ui.preferences.PreferenceActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : BaseViewBindingFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate), PodcastClickListener, SettingClickListener{

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel = HomeViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        val homeAdapter = HomeAdapter(this, this)
        with(binding.rvHome) {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        viewModel.getBestPodcasts()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homeState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
                .collect {
                    Timber.d("Find collect data: $it")
                    when (it) {
                        is HomeDataState.Idle -> {
                            homeAdapter.loadMore(false)
                        }
                        is HomeDataState.Loading -> {
                            Timber.d("HomeDataState.Loading")
                            homeAdapter.loadMore(true)
                        }
                        is HomeDataState.Success -> {
                            Timber.d("homeAdapter.updateData(list)")
                            homeAdapter.loadMore(false)
                            homeAdapter.updateData(it.data)
                        }
                        is HomeDataState.Error<*> -> {
                            Timber.e("HomeDataState.Error: $it.error")
                            homeAdapter.loadMore(false)
                        }
                    }
                }
        }
    }

    override fun onPodcastClick(podcast: BestPodcastsBody.Podcast) {

    }

    override fun onSettingClick() {
        val intent = Intent(requireContext(), PreferenceActivity::class.java)
        startActivity(intent)
    }
}
