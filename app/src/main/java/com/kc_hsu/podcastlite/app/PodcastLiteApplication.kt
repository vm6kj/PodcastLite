package com.kc_hsu.podcastlite.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.kc_hsu.podcastlite.di.*
import com.kc_hsu.podcastlite.preferences.UserPreferences
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class PodcastLiteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        initDI()
        initLoggingModule()
        UserPreferences.init(this)
    }

    private fun initDI() {
        startKoin {
            androidContext(this@PodcastLiteApplication)
            fragmentFactory()
            modules(
                fragmentModule,
                viewModelModule,
                listAdapterModule,
                networkModule,
                utilModule,
                loggingModule,
                exoPlayerModule,
                dataSourceModule,
                dbModule,
                daoModule,
                podcastRepoModule
            )
        }
    }

    private fun initLoggingModule() {
        val formatStrategy: FormatStrategy = get()
        val logAdapter: AndroidLogAdapter = get { parametersOf(formatStrategy) }
        Logger.addLogAdapter(logAdapter)
        Timber.plant(get())
    }
}
