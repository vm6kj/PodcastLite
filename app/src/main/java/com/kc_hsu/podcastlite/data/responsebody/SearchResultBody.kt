package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class SearchResultBody(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next_offset")
    var nextOffset: Int?,
    @SerializedName("results")
    var results: List<Result?>?,
    @SerializedName("took")
    var took: Double?,
    @SerializedName("total")
    var total: Int?
) {
    data class Result(
        @SerializedName("description_highlighted")
        var descriptionHighlighted: String?,
        @SerializedName("description_original")
        var descriptionOriginal: String?,
        @SerializedName("earliest_pub_date_ms")
        var earliestPubDateMs: Long?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("explicit_content")
        var explicitContent: Boolean?,
        @SerializedName("genre_ids")
        var genreIds: List<Int?>?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("itunes_id")
        var itunesId: Int?,
        @SerializedName("latest_pub_date_ms")
        var latestPubDateMs: Long?,
        @SerializedName("listen_score")
        var listenScore: String?,
        @SerializedName("listen_score_global_rank")
        var listenScoreGlobalRank: String?,
        @SerializedName("listennotes_url")
        var listennotesUrl: String?,
        @SerializedName("publisher_highlighted")
        var publisherHighlighted: String?,
        @SerializedName("publisher_original")
        var publisherOriginal: String?,
        @SerializedName("rss")
        var rss: String?,
        @SerializedName("thumbnail")
        var thumbnail: String?,
        @SerializedName("title_highlighted")
        var titleHighlighted: String?,
        @SerializedName("title_original")
        var titleOriginal: String?,
        @SerializedName("total_episodes")
        var totalEpisodes: Int?,
        @SerializedName("website")
        var website: String?
    )
}
