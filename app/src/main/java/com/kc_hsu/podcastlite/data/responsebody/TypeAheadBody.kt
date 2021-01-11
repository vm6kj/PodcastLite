package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class TypeAheadBody(
    @SerializedName("genres")
    var genres: List<Any?>?,
    @SerializedName("podcasts")
    var podcasts: List<Podcast?>?,
    @SerializedName("terms")
    var terms: List<String?>?
) {
    data class Podcast(
        @SerializedName("explicit_content")
        var explicitContent: Boolean?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("publisher_highlighted")
        var publisherHighlighted: String?,
        @SerializedName("publisher_original")
        var publisherOriginal: String?,
        @SerializedName("thumbnail")
        var thumbnail: String?,
        @SerializedName("title_highlighted")
        var titleHighlighted: String?,
        @SerializedName("title_original")
        var titleOriginal: String?
    )
}
