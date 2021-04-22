package com.kc_hsu.podcastlite.ui.home.controller

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagedListEpoxyController
import com.kc_hsu.podcastlite.CuratedPodcastBindingModel_
import com.kc_hsu.podcastlite.data.responsebody.CuratedPodcastsNoIdBody

class CuratedPodcastController :
    PagedListEpoxyController<CuratedPodcastsNoIdBody.CuratedLists.Podcast>(diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()) {
    override fun buildItemModel(
        currentPosition: Int,
        item: CuratedPodcastsNoIdBody.CuratedLists.Podcast?
    ): EpoxyModel<*> {
        return item?.run {
            CuratedPodcastBindingModel_()
                .id(this.id)
                .podcast(this)
        } ?: run {
            CuratedPodcastBindingModel_()
                .id(-currentPosition)
        }
    }
}
