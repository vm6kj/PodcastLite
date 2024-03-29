package com.kc_hsu.podcastlite.di

import androidx.room.Room
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kc_hsu.podcastlite.data.PodcastClient
import com.kc_hsu.podcastlite.data.PodcastRepo
import com.kc_hsu.podcastlite.data.PodcastRepoImpl
import com.kc_hsu.podcastlite.data.datasource.BestPodcastDataSourceFactory
import com.kc_hsu.podcastlite.data.local.PodcastDatabase
import com.kc_hsu.podcastlite.screen.album.AlbumFragment
import com.kc_hsu.podcastlite.screen.album.AlbumViewModel
import com.kc_hsu.podcastlite.screen.home.HomeFragment
import com.kc_hsu.podcastlite.screen.home.HomeViewModel
import com.kc_hsu.podcastlite.screen.preferences.MainPreferenceFragment
import com.kc_hsu.podcastlite.screen.search.SearchFragment
import com.kc_hsu.podcastlite.screen.search.SearchViewModel
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

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PodcastDatabase::class.java,
            PodcastDatabase::class.java.simpleName,
        ).fallbackToDestructiveMigration().build()
    }
}

val daoModule = module {
    single { get<PodcastDatabase>().podcastDao() }
}

val podcastRepoModule = module {
    single<PodcastRepo> { PodcastRepoImpl(get(), get()) }
}
