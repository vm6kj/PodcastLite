package com.kc_hsu.podcastlite.data

import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PodcastApiService {
    @GET("getcasts")
    @Headers("Accept: application/json")
    suspend fun getCasts(): Response<PodcastBody>

    @GET("getcastdetail")
    @Headers("Accept: application/json")
    suspend fun getcastdetail(): Response<PodcastDetailBody>
}