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
        podcastApi.search("股癌").run {
            assertTrue(isSuccessful)
            println("testSearch= ${body()}")
        }
    }

    @Test
    fun testTypeHead() = runBlocking {
        podcastApi.typeahead("股癌").run {
            assertTrue(isSuccessful)
            println("testTypeHead= ${body()}")
        }
    }

    @Test
    fun testBestPodcasts() = runBlocking {
        podcastApi.bestPodcasts(93).run {
            assertTrue(isSuccessful)
            println("testBestPodcasts= ${body()}")
        }
    }

    @Test
    fun testPodcasts() = runBlocking {
        podcastApi.podcastsById("4d3fe717742d4963a85562e9f84d8c79").run {
            assertTrue(isSuccessful)
            println("testPodcasts= ${body()}")
        }
    }

    @Test
    fun testEpisodes() = runBlocking {
        podcastApi.episodes("6b6d65930c5a4f71b254465871fed370").run {
            assertTrue(isSuccessful)
            println("testEpisodes= ${body()}")
        }
    }

    @Test
    fun testCuratedPodcasts() = runBlocking {
        podcastApi.curatedPodcasts("SDFKduyJ47r").run {
            assertTrue(isSuccessful)
            println("testCuratedPodcasts= ${body()}")
        }
    }

    @Test
    fun testGenres() = runBlocking {
        podcastApi.genres().run {
            assertTrue(isSuccessful)
            println("testGenres= ${body()}")
        }
    }

    @Test
    fun testRegions() = runBlocking {
        podcastApi.regions().run {
            assertTrue(isSuccessful)
            println("testRegions= ${body()}")
        }
    }

    @Test
    fun testLanguages() = runBlocking {
        podcastApi.languages().run {
            assertTrue(isSuccessful)
            println("testLanguages= ${body()}")
        }
    }

    @Test
    fun testJustListen() = runBlocking {
        podcastApi.justListen().run {
            assertTrue(isSuccessful)
            println("testJustListen= ${body()}")
        }
    }

    @Test
    fun testCuratedPodcastsNoId() = runBlocking {
        podcastApi.curatedPodcastsNoId().run {
            assertTrue(isSuccessful)
            println("testCuratedPodcastsNoId= ${body()}")
        }
    }

    @Test
    fun testPodcastsRecommendations() = runBlocking {
        podcastApi.podcastsRecommendations("25212ac3c53240a880dd5032e547047b").run {
            assertTrue(isSuccessful)
            println("testPodcastsRecommendations= ${body()}")
        }
    }

    @Test
    fun testEpisodesRecommendations() = runBlocking {
        podcastApi.episodesRecommendations("914a9deafa5340eeaa2859c77f275799").run {
            assertTrue(isSuccessful)
            println("testEpisodesRecommendations= ${body()}")
        }
    }

    @Test
    fun testPlaylists() = runBlocking {
        podcastApi.playlists().run {
            assertTrue(isSuccessful)
            println("testPlaylists= ${body()}")
        }
    }

    @Test
    fun testPlaylistsWithId() = runBlocking {
        podcastApi.playlistsWithId("m1pe7z60bsw").run {
            assertTrue(isSuccessful)
            println("testPlaylistsWithId= ${body()}")
        }
    }
}
