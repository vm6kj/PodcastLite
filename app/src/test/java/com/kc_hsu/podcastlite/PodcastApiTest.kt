package com.kc_hsu.podcastlite

import com.kc_hsu.podcastlite.data.PodcastApi
import com.kc_hsu.podcastlite.data.PodcastClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PodcastApiTest {
    private lateinit var podcastApi: PodcastApi

    @Before
    fun setUp() {
        podcastApi = PodcastClient.getApiClient()
    }

    @Test
    fun testSearch() = runBlocking {
        val response = podcastApi.search("股癌")
        assertTrue(response.isSuccessful)
        println("testSearch= ${response.body()}")
    }

    @Test
    fun testTypeHead() = runBlocking {
        val response = podcastApi.typeahead("股癌")
        assertTrue(response.isSuccessful)
        println("testTypeHead= ${response.body()}")
    }
}