package com.kc_hsu.podcastlite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.podcastListFragment -> {
                    toolbar.title = getString(R.string.app_name)
                }
                R.id.podcastDetailFragment -> {
                    toolbar.title = ""
                }
                R.id.podcastPlayerFragment -> {
                    toolbar.title = ""
                }
            }
        }
    }

    // Avoid leaking on Android Q devices, refer to: https://issuetracker.google.com/issues/139738913
    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            finishAfterTransition()
        }
    }
}