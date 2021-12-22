package com.kc_hsu.podcastlite.screen.album

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.AutoClearedValue
import com.kc_hsu.podcastlite.base.BaseViewBindingFragment
import com.kc_hsu.podcastlite.databinding.AlbumFragmentBinding
import com.kc_hsu.podcastlite.screen.album.tabs.RecentAddedFragment
import com.kc_hsu.podcastlite.screen.album.tabs.TotalAddedFragment
import timber.log.Timber
import java.lang.IllegalStateException

class AlbumFragment : BaseViewBindingFragment<AlbumFragmentBinding>(AlbumFragmentBinding::inflate) {

    companion object {
        fun newInstance() = AlbumFragment()
    }
    private lateinit var tabItemTitles: List<String>

    private var viewModel = AlbumViewModel()
    private var tabLayoutMediator by AutoClearedValue<TabLayoutMediator>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabItemTitles = arrayListOf(getString(R.string.tab_name_recent_added), getString(R.string.tab_name_total_added))
        with(binding.vpHostInAlbum) {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    // tablayout change
                    binding.tablayoutAlbum.getTabAt(position)?.select()
                }
            })

            adapter = object : FragmentStateAdapter(this@AlbumFragment) {
                override fun getItemCount() = 2

                override fun createFragment(position: Int): Fragment {
                    Timber.d("KCTEST position $position")
                    return when (position) {
                        0 -> RecentAddedFragment.newInstance()
                        1 -> TotalAddedFragment.newInstance()
                        else -> throw IllegalStateException("Error fragment position!")
                    }
                }
            }
        }

        binding.tablayoutAlbum.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Timber.d("KCTEST tab?.position: ${tab?.position}")
                when (tab?.position) {
                    0 -> RecentAddedFragment.newInstance()
                    1 -> TotalAddedFragment.newInstance()
                    else -> throw IllegalStateException("Error fragment position!")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                // tv_album_fragment.text = it
            }
        )

        tabLayoutMediator = TabLayoutMediator(binding.tablayoutAlbum, binding.vpHostInAlbum) { tab, position ->
            tab.text = tabItemTitles[position]
        }

        tabLayoutMediator.attach()
    }
}
