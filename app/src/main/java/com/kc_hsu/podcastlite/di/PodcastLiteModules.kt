package com.kc_hsu.podcastlite.di

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kc_hsu.podcastlite.data.PodcastClient
import com.kc_hsu.podcastlite.ui.podcastdetail.PodcastDetailAdapter
import com.kc_hsu.podcastlite.ui.podcastdetail.PodcastDetailFragment
import com.kc_hsu.podcastlite.ui.podcastdetail.PodcastDetailViewModel
import com.kc_hsu.podcastlite.ui.podcastlist.PodcastListAdapter
import com.kc_hsu.podcastlite.ui.podcastlist.PodcastListFragment
import com.kc_hsu.podcastlite.ui.podcastlist.PodcastListViewModel
import com.kc_hsu.podcastlite.ui.podcastplayer.PodcastPlayerFragment
import com.kc_hsu.podcastlite.ui.podcastplayer.PodcastPlayerViewModel
import com.kc_hsu.podcastlite.utils.Mp3PlayerStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    fragment { PodcastListFragment(get()) }
    fragment { PodcastDetailFragment(get()) }
    fragment { PodcastPlayerFragment() }
}

val viewModelModule = module {
    viewModel { PodcastListViewModel() }
    viewModel { PodcastDetailViewModel() }
    viewModel { PodcastPlayerViewModel() }
}

val listAdapterModule = module {
    factory { (podcastListViewModel: PodcastListViewModel) ->
        PodcastListAdapter(podcastListViewModel)
    }

    factory { (podcastDetailViewModel: PodcastDetailViewModel) ->
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
