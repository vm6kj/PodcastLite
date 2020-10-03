package com.kc_hsu.podcastlite.di

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
