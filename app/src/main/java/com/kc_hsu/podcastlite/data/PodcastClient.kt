package com.kc_hsu.podcastlite.data

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object PodcastClient {

    // private const val BASE_URL = "https://listen-api.listennotes.com/api/v2/"
    private const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"

    // TODO Move to Config d904a41a09154c59b3180b0369bd079b
    private const val API_KEY = ""

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
            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
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
