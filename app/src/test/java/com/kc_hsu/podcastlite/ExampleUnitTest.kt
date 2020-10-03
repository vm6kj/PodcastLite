package com.kc_hsu.podcastlite

import com.kc_hsu.podcastlite.data.PodcastRepo
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.koin.core.KoinComponent

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : KoinComponent {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        runBlocking {
            val tmp = PodcastRepo.getPodcastList()
            System.out.println("KCTEST" + tmp)
        }
    }
}