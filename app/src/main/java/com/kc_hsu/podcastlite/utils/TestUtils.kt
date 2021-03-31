package com.kc_hsu.podcastlite.utils

import android.app.Activity
import com.google.gson.Gson
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import java.io.BufferedReader
import java.io.InputStreamReader

object TestUtils {

    fun getTestPodcastList(activity: Activity): List<BestPodcastsBody.Podcast> {
        val buffer = BufferedReader(InputStreamReader(activity.assets.open("TestBestPodcast.json")))
        return Gson().fromJson<BestPodcastsBody>(buffer, BestPodcastsBody::class.java).podcasts as List<BestPodcastsBody.Podcast>
    }
}
