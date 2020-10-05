package com.kc_hsu.podcastlite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

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