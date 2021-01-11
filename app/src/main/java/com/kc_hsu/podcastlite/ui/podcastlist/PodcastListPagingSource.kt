package com.kc_hsu.podcastlite.ui.podcastlist

import androidx.paging.PagingSource
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import org.koin.core.KoinComponent
import timber.log.Timber

class PodcastListPagingSource : PagingSource<Int, PodcastBody.Data.Podcast>(), KoinComponent {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PodcastBody.Data.Podcast> {
        return try {
            Timber.d("Load podcast data from remote...")
            val podcastList = PodcastRepo.getPodcastList()
            LoadResult.Page(podcastList!!, null, null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
