package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class PlaylistsBody(
    @SerializedName("has_next")
    var hasNext: Boolean?,
    @SerializedName("has_previous")
    var hasPrevious: Boolean?,
    @SerializedName("next_page_number")
    var nextPageNumber: Int?,
    @SerializedName("page_number")
    var pageNumber: Int?,
    @SerializedName("playlists")
    var playlists: List<Playlists?>?,
    @SerializedName("previous_page_number")
    var previousPageNumber: Int?,
    @SerializedName("total")
    var total: Int?
) {
    data class Playlists(
        @SerializedName("description")
        var description: String?,
        @SerializedName("episode_count")
        var episodeCount: Int?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("listennotes_url")
        var listennotesUrl: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("podcast_count")
        var podcastCount: Int?,
        @SerializedName("thumbnail")
        var thumbnail: String?,
        @SerializedName("visibility")
        var visibility: String?
    )
}
