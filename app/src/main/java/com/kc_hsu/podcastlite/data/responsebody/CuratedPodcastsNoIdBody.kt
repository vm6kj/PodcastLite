package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class CuratedPodcastsNoIdBody(
    @SerializedName("curated_lists")
    var curatedLists: List<CuratedLists?>?,
    @SerializedName("has_next")
    var hasNext: Boolean?,
    @SerializedName("has_previous")
    var hasPrevious: Boolean?,
    @SerializedName("next_page_number")
    var nextPageNumber: Int?,
    @SerializedName("page_number")
    var pageNumber: Int?,
    @SerializedName("previous_page_number")
    var previousPageNumber: Int?,
    @SerializedName("total")
    var total: Int?
) {
    data class CuratedLists(
        @SerializedName("description")
        var description: String?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("listennotes_url")
        var listennotesUrl: String?,
        @SerializedName("podcasts")
        var podcasts: List<Podcast?>?,
        @SerializedName("pub_date_ms")
        var pubDateMs: Long?,
        @SerializedName("source_domain")
        var sourceDomain: String?,
        @SerializedName("source_url")
        var sourceUrl: String?,
        @SerializedName("title")
        var title: String?
    ) {
        data class Podcast(
            @SerializedName("id")
            var id: String?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("listen_score")
            var listenScore: String?,
            @SerializedName("listen_score_global_rank")
            var listenScoreGlobalRank: String?,
            @SerializedName("listennotes_url")
            var listennotesUrl: String?,
            @SerializedName("publisher")
            var publisher: String?,
            @SerializedName("thumbnail")
            var thumbnail: String?,
            @SerializedName("title")
            var title: String?
        )
    }
}
