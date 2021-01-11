package com.kc_hsu.podcastlite.data

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object PodcastClient {

    private const val BASE_URL = "https://listen-api.listennotes.com/api/v2/"

    // TODO Move to Config
    private const val API_KEY = "b1b2deeb0e5f453fa9183d3ce0fa9653"

    fun getApiClient(): PodcastApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.d(it) })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val headerInInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().addHeader("X-ListenAPI-Key", API_KEY).build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(headerInInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(HttpUrl.parse(BASE_URL)!!)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(PodcastApi::class.java)
    }
}
