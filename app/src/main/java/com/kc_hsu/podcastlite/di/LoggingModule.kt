package com.kc_hsu.podcastlite.di

import android.util.Log
import com.kc_hsu.podcastlite.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.dsl.module
import timber.log.Timber

val loggingModule = module {
    single<AndroidLogAdapter> { (formatStrategy: FormatStrategy) ->
        object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        }
    }

    single<FormatStrategy> {
        PrettyFormatStrategy.newBuilder()
            .tag("KCTEST")
            .methodCount(3)
            .methodOffset(5) // avoid timber internal stack track
            .build()
    }

    single<Timber.Tree> { TimberLoggerDebugTree() }
}

private class TimberLoggerDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.VERBOSE -> Logger.v(message)
            Log.DEBUG -> Logger.d(message)
            Log.INFO -> Logger.i(message)
            Log.WARN -> Logger.w(message)
            Log.ERROR -> Logger.e(t, message)
            else -> Logger.wtf(message)
        }
    }
}
