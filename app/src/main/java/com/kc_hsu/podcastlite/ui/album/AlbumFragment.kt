package com.kc_hsu.podcastlite.ui.album

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.AutoClearedValue
import com.kc_hsu.podcastlite.base.BaseBindingFragment
import com.kc_hsu.podcastlite.databinding.AlbumFragmentBinding
import com.kc_hsu.podcastlite.ui.album.tabs.RecentAddedFragment
import com.kc_hsu.podcastlite.ui.album.tabs.TotalAddedFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber
import java.lang.IllegalStateException

class AlbumFragment :
    BaseBindingFragment<AlbumFragmentBinding, AlbumViewModel>(R.layout.album_fragment),
    KoinComponent {

    override val viewModel: AlbumViewModel by viewModel()

    companion object {
        fun newInstance() = AlbumFragment()
    }

    private var tabLayoutMediator by AutoClearedValue<TabLayoutMediator>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
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

        binding.tablayoutAlbum.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
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
            tab.text = "AAAAA"
        }

        tabLayoutMediator.attach()
    }
}
