package com.kc_hsu.podcastlite.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.data.local.PodcastDao
import org.koin.core.inject
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import com.kc_hsu.podcastlite.screen.podcastepisode.PodcastEpisodeFragment
import com.kc_hsu.podcastlite.screen.preferences.PreferenceActivity
import com.kc_hsu.podcastlite.utils.openFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber

class HomeFragment : BaseViewBindingFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate), PodcastClickListener, SettingClickListener, KoinComponent {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by activityViewModels<HomeViewModel>()

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

    override fun onPodcastClick(podcast: BestPodcastModel) {
        openFragment(PodcastEpisodeFragment.newInstance(podcast), true)
    }

    override fun onSettingClick() {
        val intent = Intent(requireContext(), PreferenceActivity::class.java)
        startActivity(intent)
    }
}
