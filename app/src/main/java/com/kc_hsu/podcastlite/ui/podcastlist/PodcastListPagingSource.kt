package com.kc_hsu.podcastlite.ui.podcastlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import org.koin.core.KoinComponent
import timber.log.Timber

@Deprecated("Unused")
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

    override fun getRefreshKey(state: PagingState<Int, PodcastBody.Data.Podcast>): Int? {
        return 0
    }
}
