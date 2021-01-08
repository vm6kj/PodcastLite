package com.kc_hsu.podcastlite.data.responsebody


import com.google.gson.annotations.SerializedName

data class TypeAheadBody(
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("podcasts")
    val podcasts: List<Podcast>,
    @SerializedName("terms")
    val terms: List<String>
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("parent_id")
        val parentId: Int
    )

    data class Podcast(
        @SerializedName("explicit_content")
        val explicitContent: Boolean,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: String,
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