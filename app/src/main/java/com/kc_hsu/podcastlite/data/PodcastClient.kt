package com.kc_hsu.podcastlite.data

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object PodcastClient {

    private const val BASE_URL = "https://demo4491005.mockable.io/"

    fun getApiClient(): PodcastApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.d(it) })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(HttpUrl.parse(BASE_URL)!!)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(PodcastApiService::class.java)
    }
}