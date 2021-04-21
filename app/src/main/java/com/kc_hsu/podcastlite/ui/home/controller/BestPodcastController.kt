package com.kc_hsu.podcastlite.ui.home.controller

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagedListEpoxyController
import com.kc_hsu.podcastlite.BestPodcastBindingModel_
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody

class BestPodcastController : PagedListEpoxyController<BestPodcastsBody.Podcast>(diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()) {

    var loadMore = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(
        currentPosition: Int,
        item: BestPodcastsBody.Podcast?
    ): EpoxyModel<*> {
        return item?.run {
            BestPodcastBindingModel_()
                .id(this.id)
                .podcast(this)
        } ?: run {
            BestPodcastBindingModel_()
                .id(-currentPosition)
        }
    }

    // TODO Adding a loading view
    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
    }
}
