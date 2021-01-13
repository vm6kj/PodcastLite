package com.kc_hsu.podcastlite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kc_hsu.podcastlite.preferences.UserPreferences
import com.kc_hsu.podcastlite.ui.album.AlbumFragment
import com.kc_hsu.podcastlite.ui.home.HomeFragment
import com.kc_hsu.podcastlite.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(UserPreferences.getTheme())
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_main)

        bvn_host.setOnNavigationItemSelectedListener(this)
        vp_host.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bvn_host.menu.getItem(position).isChecked = true
            }
        })
        vp_host.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment.newInstance()
                    1 -> SearchFragment.newInstance()
                    2 -> AlbumFragment.newInstance()
                    else -> throw IllegalStateException("Error fragment position!")
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        vp_host.currentItem = item.order
        return true
    }
}
