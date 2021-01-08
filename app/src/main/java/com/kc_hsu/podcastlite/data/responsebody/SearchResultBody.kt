package com.kc_hsu.podcastlite.data.responsebody


import com.google.gson.annotations.SerializedName

data class SearchResultBody(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next_offset")
    val nextOffset: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("took")
    val took: Double,
    @SerializedName("total")
    val total: Int
) {
    data class Result(
        @SerializedName("audio")
        val audio: String,
        @SerializedName("audio_length_sec")
        val audioLengthSec: Int,
        @SerializedName("description_highlighted")
        val descriptionHighlighted: String,
        @SerializedName("description_original")
        val descriptionOriginal: String,
        @SerializedName("explicit_content")
        val explicitContent: Boolean,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("itunes_id")
        val itunesId: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("listennotes_url")
        val listennotesUrl: String,
        @SerializedName("podcast")
        val podcast: Podcast,
        @SerializedName("pub_date_ms")
        val pubDateMs: Long,
        @SerializedName("rss")
        val rss: String,
        @SerializedName("thumbnail")
        val thumbnail: String,
        @SerializedName("title_highlighted")
        val titleHighlighted: String,
        @SerializedName("title_original")
        val titleOriginal: String,
        @SerializedName("transcripts_highlighted")
        val transcriptsHighlighted: List<Any>
    ) {
        data class Podcast(
            @SerializedName("genre_ids")
            val genreIds: List<Int>,
            @SerializedName("id")
            val id: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("listen_score")
            val listenScore: String,
            @SerializedName("listen_score_global_rank")
            val listenScoreGlobalRank: String,
            @SerializedName("listennotes_url")
            val listennotesUrl: String,
            @SerializedName("publisher_highlighted")
            val publisherHighlighted: String,
            @SerializedName("publisher_original")
            val publisherOriginal: String,
            @SerializedName("thumbnail")
            val thumbnail: String,
            @SerializedName("title_highlighted")
            val titleHighlighted: String,
            @SerializedName("title_original")
            val titleOriginal: String
        )
    }
}