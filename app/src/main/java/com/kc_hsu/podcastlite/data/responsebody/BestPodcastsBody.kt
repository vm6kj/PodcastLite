package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class BestPodcastsBody(
    @SerializedName("has_next")
    var hasNext: Boolean?,
    @SerializedName("has_previous")
    var hasPrevious: Boolean?,
    @SerializedName("id")
    // genre_id
    var id: Int?,
    @SerializedName("listennotes_url")
    var listennotesUrl: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("next_page_number")
    var nextPageNumber: Int?,
    @SerializedName("page_number")
    var pageNumber: Int?,
    @SerializedName("parent_id")
    var parentId: Int?,
    @SerializedName("podcasts")
    var podcasts: List<Podcast>?,
    @SerializedName("previous_page_number")
    var previousPageNumber: Int?,
    @SerializedName("total")
    var total: Int?
) {
    data class Podcast(
        @SerializedName("country")
        var country: String?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("earliest_pub_date_ms")
        var earliestPubDateMs: Long?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("explicit_content")
        var explicitContent: Boolean?,
        @SerializedName("extra")
        var extra: Extra?,
        @SerializedName("genre_ids")
        var genreIds: List<Int?>?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("is_claimed")
        var isClaimed: Boolean?,
        @SerializedName("itunes_id")
        var itunesId: Int?,
        @SerializedName("language")
        var language: String?,
        @SerializedName("latest_pub_date_ms")
        var latestPubDateMs: Long?,
        @SerializedName("listen_score")
        var listenScore: String?,
        @SerializedName("listen_score_global_rank")
        var listenScoreGlobalRank: String?,
        @SerializedName("listennotes_url")
        var listennotesUrl: String?,
        @SerializedName("looking_for")
        var lookingFor: LookingFor?,
        @SerializedName("publisher")
        var publisher: String?,
        @SerializedName("rss")
        var rss: String?,
        @SerializedName("thumbnail")
        var thumbnail: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("total_episodes")
        var totalEpisodes: Int?,
        @SerializedName("type")
        var type: String?,
        @SerializedName("website")
        var website: String?
    ) {
        data class Extra(
            @SerializedName("facebook_handle")
            var facebookHandle: String?,
            @SerializedName("google_url")
            var googleUrl: String?,
            @SerializedName("instagram_handle")
            var instagramHandle: String?,
            @SerializedName("linkedin_url")
            var linkedinUrl: String?,
            @SerializedName("patreon_handle")
            var patreonHandle: String?,
            @SerializedName("spotify_url")
            var spotifyUrl: String?,
            @SerializedName("twitter_handle")
            var twitterHandle: String?,
            @SerializedName("url1")
            var url1: String?,
            @SerializedName("url2")
            var url2: String?,
            @SerializedName("url3")
            var url3: String?,
            @SerializedName("wechat_handle")
            var wechatHandle: String?,
            @SerializedName("youtube_url")
            var youtubeUrl: String?
        )

        data class LookingFor(
            @SerializedName("cohosts")
            var cohosts: Boolean?,
            @SerializedName("cross_promotion")
            var crossPromotion: Boolean?,
            @SerializedName("guests")
            var guests: Boolean?,
            @SerializedName("sponsors")
            var sponsors: Boolean?
        )
    }
}
