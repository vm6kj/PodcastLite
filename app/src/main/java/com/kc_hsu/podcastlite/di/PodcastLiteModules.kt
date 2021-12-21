package com.kc_hsu.podcastlite.di

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kc_hsu.podcastlite.data.PodcastClient
import com.kc_hsu.podcastlite.data.datasource.BestPodcastDataSourceFactory
import com.kc_hsu.podcastlite.ui.album.AlbumFragment
import com.kc_hsu.podcastlite.ui.album.AlbumViewModel
import com.kc_hsu.podcastlite.ui.home.HomeFragment
import com.kc_hsu.podcastlite.ui.home.HomeViewModel
import com.kc_hsu.podcastlite.ui.preferences.MainPreferenceFragment
import com.kc_hsu.podcastlite.ui.search.SearchFragment
import com.kc_hsu.podcastlite.ui.search.SearchViewModel
import com.kc_hsu.podcastlite.utils.Mp3PlayerStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {

    fragment { HomeFragment() }
    fragment { SearchFragment() }
    fragment { AlbumFragment() }
    fragment { MainPreferenceFragment() }
}

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { SearchViewModel() }
    viewModel { AlbumViewModel() }
}

val listAdapterModule = module {
}

val networkModule = module {
    single {
        PodcastClient.getApiClient()
    }
}

val utilModule = module {
    single {
        CoroutineScope(Dispatchers.IO)
    }
}

val exoPlayerModule = module {
    factory { (url: String, mp3PlayerStateHolder: Mp3PlayerStateHolder) ->
        SimpleExoPlayer.Builder(androidApplication()).build().apply {
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            playWhenReady = mp3PlayerStateHolder.playWhenReady
            repeatMode = Player.REPEAT_MODE_ALL
            seekTo(mp3PlayerStateHolder.currentWindow, mp3PlayerStateHolder.playbackPosition)
            prepare()
            play()
        }
    }
}

val dataSourceModule = module {
    factory { (genreId: Int) ->
        BestPodcastDataSourceFactory(genreId)
    }
}
