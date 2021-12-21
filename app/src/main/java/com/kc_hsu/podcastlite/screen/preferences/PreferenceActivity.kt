package com.kc_hsu.podcastlite.screen.preferences

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.FrameLayout
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.base.BaseActivity
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class PreferenceActivity : BaseActivity() {
    companion object {
        private const val TAG_FRAGMENT = "tag_preference"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val root = FrameLayout(this)
        root.id = R.id.content
        root.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContentView(root)

        // TODO How to start the first preference using navigation component while using res/xml/xxx.xml as the preferences fragment
        if (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content, MainPreferenceFragment.newInstance(), TAG_FRAGMENT)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else {
                supportFragmentManager.popBackStack()
            }
            return true
        }
        return false
    }
}
