package com.kc_hsu.podcastlite.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.databinding.HomeFragmentBinding
import com.kc_hsu.podcastlite.screen.podcastepisode.PodcastEpisodeFragment
import com.kc_hsu.podcastlite.screen.preferences.PreferenceActivity
import com.kc_hsu.podcastlite.utils.openFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

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

        val observer = object : Observer<HomeDataState> {
            override fun onChanged(it: HomeDataState?) {
                when (it) {
                    is HomeDataState.Idle -> {
                        homeAdapter.loadMore(false)
                    }
                    is HomeDataState.Loading -> {
                        homeAdapter.loadMore(true)
                    }
                    is HomeDataState.Success -> {
                        homeAdapter.loadMore(false)
                        homeAdapter.updateData(it.data)
                    }
                    is HomeDataState.Error<*> -> {
                        homeAdapter.loadMore(false)
                    }
                    is HomeDataState.Done -> {
                        homeAdapter.loadMore(false)
                        viewModel.homeStateLiveData.removeObserver(this)
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.homeStateLiveData.observeForever(observer)
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
