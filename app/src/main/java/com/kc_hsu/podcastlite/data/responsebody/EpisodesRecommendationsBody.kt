package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class EpisodesRecommendationsBody(
    @SerializedName("recommendations")
    var recommendations: List<Recommendation?>?
) {
    data class Recommendation(
        @SerializedName("audio")
        var audio: String?,
        @SerializedName("audio_length_sec")
        var audioLengthSec: Int?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("explicit_content")
        var explicitContent: Boolean?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("link")
        var link: String?,
        @SerializedName("listennotes_edit_url")
        var listennotesEditUrl: String?,
        @SerializedName("listennotes_url")
        var listennotesUrl: String?,
        @SerializedName("maybe_audio_invalid")
        var maybeAudioInvalid: Boolean?,
        @SerializedName("podcast")
        var podcast: Podcast?,
        @SerializedName("pub_date_ms")
        var pubDateMs: Long?,
        @SerializedName("thumbnail")
        var thumbnail: String?,
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
