package com.kc_hsu.podcastlite.ui.podcastdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import timber.log.Timber
import java.lang.StringBuilder

class PodcastDetailPagingSource(private val podcastCoverData: MutableLiveData<PodcastCoverData>) : PagingSource<Int, PodcastDetailBody.Data.Collection.ContentFeed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PodcastDetailBody.Data.Collection.ContentFeed> {
        return try {
            Timber.d("Load podcast detail data  from remote...")
            val podcastDetailBody = PodcastRepo.getPodcastDetail()!!
            val collection = podcastDetailBody.data.collection
            val data = PodcastCoverData(collection.artworkUrl100, collection.collectionName, collection.artistName)
            podcastCoverData.postValue(data)
            LoadResult.Page(podcastDetailBody.data.collection.contentFeed, null, null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}