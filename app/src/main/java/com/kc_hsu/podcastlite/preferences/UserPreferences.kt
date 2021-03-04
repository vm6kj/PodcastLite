package com.kc_hsu.podcastlite.preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.annotation.IntDef
import androidx.annotation.StringDef
import androidx.preference.PreferenceManager
import com.kc_hsu.podcastlite.R
import timber.log.Timber

object UserPreferences {

    /**
     * preference keys can be refer to: /app/src/main/res/xml
     */
    const val PREF_THEME = "prefTheme"

    const val THEME_SYSTEM = "system"
    const val THEME_LIGHT = "0"
    const val THEME_DARK = "1"

    private lateinit var context: Context

    @JvmStatic
    private lateinit var prefs: SharedPreferences

    @StringDef(THEME_SYSTEM, THEME_LIGHT, THEME_DARK)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class ThemeValue

    @JvmStatic
    fun init(context: Context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        this.context = context.applicationContext
    }

    @JvmStatic
    fun getTheme(): Int {
        Timber.d("Current style: ${prefs.getString(PREF_THEME, THEME_SYSTEM)}")
        return when (prefs.getString(PREF_THEME, THEME_SYSTEM)) {
            THEME_SYSTEM -> {
                val nightMode = context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
                if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                    R.style.Theme_PodcastLite_Dark
                } else {
                    R.style.Theme_PodcastLite_Light
                }
            }
            THEME_LIGHT -> R.style.Theme_PodcastLite_Light
            THEME_DARK -> R.style.Theme_PodcastLite_Dark
            else -> throw IllegalStateException("Failed to get theme preference.")
        }
    }
}
