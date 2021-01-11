package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class GenresBody(
    @SerializedName("genres")
    val genres: List<Genre?>?
) {
    data class Genre(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("parent_id")
        val parentId: Int?
    )
}
