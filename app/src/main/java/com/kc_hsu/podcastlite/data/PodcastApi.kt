package com.kc_hsu.podcastlite.data

import com.kc_hsu.podcastlite.data.responsebody.*
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.data.responsebody.SearchResultBody
import com.kc_hsu.podcastlite.data.responsebody.TypeAheadBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API Reference:
 * @see <a href="https://www.listennotes.com/api/docs/">Listen API Documentation</a>
 */
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
        @Query("sort_by_date") sortByDate: Int? = null,
        @Query("type") type: String? = null,
        @Query("offset") offset: Int? = 0,
        @Query("len_min") lenMin: Int? = null,
        @Query("len_max") lenMax: Int? = null,
        @Query("episode_count_min") episodeCountMin: Int? = null,
        @Query("episode_count_max") episodeCountMax: Int? = null,
        @Query("genre_ids") genreIds: String? = null,
        @Query("published_before") publishedBefore: Int? = null,
        @Query("published_after") publishedAfter: Int? = 0,
        @Query("only_in") onlyIn: String? = null,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
        @Query("ocid") ocid: String? = null,
        @Query("ncid") ncid: String? = null,
        @Query("safe_mode") safeMode: Int? = 0
    ): Response<SearchResultBody>

    @GET("typeahead")
    // Typeahead search
    suspend fun typeahead(
        @Query("q") query: String,
        @Query("show_podcasts") showPodcasts: Int? = null,
        @Query("show_genres") showGenres: Int? = null,
        @Query("safe_mode") safeMode: Int? = null
    ): Response<TypeAheadBody>

    @GET("best_podcasts")
    // Fetch a list of best podcasts by genre
    suspend fun bestPodcasts(
        @Query("genre_id") genreId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("region") region: String? = null,
        @Query("safe_mode") safeMode: Int? = null
    ): Response<BestPodcastsBody>

    @GET("podcasts/{id}")
    // Fetch detailed meta data and episodes for a podcast by id
    suspend fun podcastsById(
        @Path("id") podcastId: String,
        @Query("next_episode_pub_date") nextEpisodePubDate: Long? = null,
        @Query("sort") sort: String? = null
    ): Response<PodcastsBody>

    @GET("episodes/{id}")
    // Fetch detailed meta data for an episode by id
    suspend fun episodes(
        @Path("id") episodeId: String,
        @Query("show_transcript") showTranscript: Int? = null
    ): Response<Episodes>

    @GET("curated_podcasts/{id}")
    // Fetch a curated list of podcasts by id
    suspend fun curatedPodcasts(
        @Path("id") curatedListId: String
    ): Response<CuratedPodcastsBody>

    @GET("genres")
    // Fetch a list of podcast genres
    suspend fun genres(
        @Query("top_level_only") topLevelOnly: Int? = null
    ): Response<GenresBody>

    @GET("regions")
    // Fetch a list of supported countries/regions for best podcasts
    suspend fun regions(): Response<RegionsBody>

    @GET("languages")
    // Fetch a list of supported languages for podcasts
    suspend fun languages(): Response<LanguagesBody>

    @GET("just_listen")
    // Fetch a random podcast episode
    suspend fun justListen(): Response<JustListenBody>

    @GET("curated_podcasts")
    // Fetch curated lists of podcasts
    suspend fun curatedPodcastsNoId(
        @Query("page") page: Int? = null
    ): Response<CuratedPodcastsNoIdBody>

    @GET("podcasts/{id}/recommendations")
    // Fetch recommendations for a podcast
    suspend fun podcastsRecommendations(
        @Path("id") podcastId: String,
        @Query("safe_mode") safeMode: Int? = null
    ): Response<PodcastsRecommendationsBody>

    @GET("episodes/{id}/recommendations")
    // Fetch recommendations for an episode
    suspend fun episodesRecommendations(
        @Path("id") episodeId: String,
        @Query("safe_mode") safeMode: Int? = null
    ): Response<EpisodesRecommendationsBody>

    @GET("playlists")
    // Fetch a list of your playlists.
    suspend fun playlists(
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null
    ): Response<PlaylistsBody>

    @GET("playlists/{id}")
    // Fetch a playlist's info and items (i.e., episodes or podcasts).
    suspend fun playlistsWithId(
        @Path("id") playlistsId: String,
        @Query("type") type: String? = null,
        @Query("last_timestamp_ms") lastTimestampMs: Int? = null,
        @Query("sort") sort: String? = null
    ): Response<PlaylistsWithIdBody>
}
