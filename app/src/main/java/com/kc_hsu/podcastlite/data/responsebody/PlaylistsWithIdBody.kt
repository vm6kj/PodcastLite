package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class PlaylistsWithIdBody(
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("items")
    var items: List<Item?>?,
    @SerializedName("last_timestamp_ms")
    var lastTimestampMs: Long?,
    @SerializedName("listennotes_url")
    var listennotesUrl: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("thumbnail")
    var thumbnail: String?,
    @SerializedName("total")
    var total: Int?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("visibility")
    var visibility: String?
) {
    data class Item(
        @SerializedName("added_at_ms")
        var addedAtMs: Long?,
        @SerializedName("data")
        var `data`: Data?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("notes")
        var notes: String?,
        @SerializedName("type")
        var type: String?
    ) {
        data class Data(
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
}
