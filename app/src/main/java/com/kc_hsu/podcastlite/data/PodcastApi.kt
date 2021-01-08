package com.kc_hsu.podcastlite.data

import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.data.responsebody.SearchResultBody
import com.kc_hsu.podcastlite.data.responsebody.TypeAheadBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PodcastApi {
    @GET("getcasts")
    @Headers("Accept: application/json")
    suspend fun getCasts(): Response<PodcastBody>

    @GET("getcastdetail")
    @Headers("Accept: application/json")
    suspend fun getcastdetail(): Response<PodcastDetailBody>

    @GET("search")
    // Full-text search
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String? = null,
        @Query("offset") offset: Int? = null,
        @Query("region") region: String? = null
    ): Response<SearchResultBody>

    @GET("typeahead")
    // Typeahead search
    suspend fun typeahead(
        @Query("q") query: String,
        @Query("show_podcasts") showPodcasts: Int? = null,
        @Query("show_genres") showGenres: Int? = null
    ): Response<TypeAheadBody>

    @GET("best_podcasts")
    // Fetch a list of best podcasts by genre
    suspend fun bestPodcasts(
        @Query("genre_id") genreId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("region") region: String? = null,
        @Query("safe_mode") safeMode: Int? = null
    )

    @GET("podcasts/{id}")
    // Fetch detailed meta data and episodes for a podcast by id
    suspend fun podcastsById(
        @Path("id") podcastId: String,
        @Query("next_episode_pub_date") nextEpisodePubDate: Int? = null,
        @Query("sort") sort: String? = null
    )

    suspend fun 
}