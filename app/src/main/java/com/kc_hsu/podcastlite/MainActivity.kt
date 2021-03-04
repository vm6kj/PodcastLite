package com.kc_hsu.podcastlite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.kc_hsu.podcastlite.preferences.UserPreferences
import com.kc_hsu.podcastlite.ui.album.AlbumFragment
import com.kc_hsu.podcastlite.ui.home.HomeFragment
import com.kc_hsu.podcastlite.ui.preferences.PreferenceActivity
import com.kc_hsu.podcastlite.ui.search.SearchFragment
import com.kc_hsu.podcastlite.ui.view.LockableBottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.bnv_host
import kotlinx.android.synthetic.main.activity_main.ll_player
import kotlinx.android.synthetic.main.activity_main.vp_host
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import timber.log.Timber

/**
 * How to implement a ViewPager with BottomNavigationView using new Navigation Architecture Component?
 * Reference: https://stackoverflow.com/a/54355659/7871271
 */
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var sheetBehavior: LockableBottomSheetBehavior<*>
    private var lastTheme: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        lastTheme = UserPreferences.getTheme()
        setTheme(lastTheme)
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_host.setOnNavigationItemSelectedListener(this)
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
                bnv_host.menu.getItem(position).isChecked = true
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

        sheetBehavior = BottomSheetBehavior.from(ll_player) as LockableBottomSheetBehavior
        sheetBehavior.peekHeight = resources.getDimension(R.dimen.external_player_height).toInt()
        sheetBehavior.isHideable = false
        sheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    override fun onStart() {
        super.onStart()
        if (lastTheme != UserPreferences.getTheme()) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
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
        if (item.itemId == R.id.setting) {
            Timber.d("Setting icon clicked!")
            val intent = Intent(this, PreferenceActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        vp_host.currentItem = item.order
        return true
    }
}
