package com.kc_hsu.podcastlite.ui.home

import androidx.lifecycle.ViewModel
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.utils.Listing
import org.koin.core.KoinComponent

class HomeViewModel : ViewModel(), KoinComponent {

    fun getBestPodcastList(genreId: Int): Listing<BestPodcastsBody.Podcast> {
        return PodcastRepo.getBestPodcastList(genreId)
    }
}
