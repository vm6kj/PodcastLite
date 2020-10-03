package com.kc_hsu.podcastlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.ui.PodcastPlayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_main)

//        lifecycleScope.launch(Dispatchers.IO) {
//            val tmp = PodcastRepo.getPodcastList()
//            Timber.d("KCTEST getPodcastList ${tmp.toString()}")
//
//            val tmp2 = PodcastRepo.getPodcastDetailList()
//            Timber.d("KCTEST getPodcastList ${tmp2.toString()}")
//        }
//        startActivity(Intent(this, PodcastPlayerActivity::class.java))
    }
}