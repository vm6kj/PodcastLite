package com.kc_hsu.podcastlite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.kc_hsu.podcastlite.base.BaseActivity
import com.kc_hsu.podcastlite.customview.TopSheetDialog
import com.kc_hsu.podcastlite.databinding.ActivityMainBinding
import com.kc_hsu.podcastlite.ui.album.AlbumFragment
import com.kc_hsu.podcastlite.ui.home.HomeFragment
import com.kc_hsu.podcastlite.ui.preferences.PreferenceActivity
import com.kc_hsu.podcastlite.ui.search.SearchFragment
import com.kc_hsu.podcastlite.ui.view.LockableBottomSheetBehavior
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import timber.log.Timber

/**
 * How to implement a ViewPager with BottomNavigationView using new Navigation Architecture Component?
 * Reference: https://stackoverflow.com/a/54355659/7871271
 */
class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sheetBehavior: LockableBottomSheetBehavior<*>
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        binding.bnvHost.setOnNavigationItemSelectedListener(this)
        binding.vpHost.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.bnvHost.menu.getItem(position).isChecked = true
            }
        })
        binding.vpHost.adapter = object : FragmentStateAdapter(this) {
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

        sheetBehavior = BottomSheetBehavior.from(binding.llPlayer) as LockableBottomSheetBehavior
        sheetBehavior.peekHeight = resources.getDimension(R.dimen.external_player_height).toInt()
        sheetBehavior.isHideable = false
        sheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private val bottomSheetCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(view: View, state: Int) {
            Timber.d("bottomSheetCallback onStateChanged")
        }

        override fun onSlide(view: View, slideOffset: Float) {
            Timber.d("bottomSheetCallback onSlide")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_activity_setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Timber.d("Setting icon clicked!")
                val intent = Intent(this, PreferenceActivity::class.java)
                startActivity(intent)
            }

            R.id.topSheetTest -> {
                TopSheetDialog(this).apply {
                    window?.attributes?.windowAnimations = R.style.TopSheet_DialogAnimation

                    setContentView(R.layout.top_sheet_dialog)
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.vpHost.currentItem = item.order
        return true
    }
}
