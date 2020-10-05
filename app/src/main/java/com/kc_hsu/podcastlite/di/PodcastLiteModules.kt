package com.kc_hsu.podcastlite.di

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.kc_hsu.podcastlite.data.PodcastClient
import com.kc_hsu.podcastlite.ui.podcastdetail.PodcastDetailAdapter
import com.kc_hsu.podcastlite.ui.podcastdetail.PodcastDetailFragment
import com.kc_hsu.podcastlite.ui.podcastdetail.PodcastDetailViewModel
import com.kc_hsu.podcastlite.ui.podcastlist.PodcastListAdapter
import com.kc_hsu.podcastlite.ui.podcastlist.PodcastListFragment
import com.kc_hsu.podcastlite.ui.podcastlist.PodcastListViewModel
import com.kc_hsu.podcastlite.ui.podcastplayer.PodcastPlayerFragment
import com.kc_hsu.podcastlite.ui.podcastplayer.PodcastPlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    fragment { PodcastListFragment() }
    fragment { PodcastDetailFragment() }
    fragment { PodcastPlayerFragment() }
}

val viewModelModule = module {
    viewModel { PodcastListViewModel() }
    viewModel { PodcastDetailViewModel() }
    viewModel { PodcastPlayerViewModel() }
}

val listAdapterModule = module {
    single { (podcastListViewModel: PodcastListViewModel) ->
        PodcastListAdapter(podcastListViewModel)
    }

    single { (podcastDetailViewModel: PodcastDetailViewModel) ->
        PodcastDetailAdapter(podcastDetailViewModel)
    }
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
    factory<DataSource.Factory> {
        DefaultDataSourceFactory(androidApplication())
    }
    factory {
        DrmSessionManager.getDummyDrmSessionManager()
    }
    factory { (url: String) ->
        ProgressiveMediaSource.Factory(get())
            .setDrmSessionManager(get())
            .createMediaSource(MediaItem.fromUri(url))
    }
    single { (url: String) ->
        SimpleExoPlayer.Builder(androidApplication()).build().apply {
//            setMediaSource(get<ProgressiveMediaSource> { parametersOf(url) })
            setMediaItem(MediaItem.fromUri(url))
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
            play()
        }
    }

//    single {
//        SimpleExoPlayer.Builder(androidApplication()).build()
//    }
}