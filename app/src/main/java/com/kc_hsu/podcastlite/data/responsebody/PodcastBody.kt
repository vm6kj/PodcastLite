package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class PodcastBody(
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("podcast")
        val podcast: List<Podcast>
    ) {
        data class Podcast(
            @SerializedName("artistName")
            val artistName: String,
            @SerializedName("artworkUrl100")
            val artworkUrl100: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String
        )
    }
}
