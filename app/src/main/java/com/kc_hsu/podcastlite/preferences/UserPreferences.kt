package com.kc_hsu.podcastlite.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.IntDef
import androidx.preference.PreferenceManager
import com.kc_hsu.podcastlite.R
import timber.log.Timber

object UserPreferences {

    const val PREF_THEME = "prefTheme"

    const val THEME_SYSTEM = 0
    const val THEME_LIGHT = 1
    const val THEME_DARK = 2

    @JvmStatic
    private lateinit var prefs: SharedPreferences

    @IntDef(THEME_SYSTEM, THEME_LIGHT, THEME_DARK)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class ThemeValue

    @JvmStatic
    fun init(context: Context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @JvmStatic
    fun setThemeValue(@ThemeValue themeValue: Int) {
        prefs.edit().putInt(PREF_THEME, themeValue).apply()
    }

    @JvmStatic
    fun getTheme(): Int {
        Timber.d("Current style: ${prefs.getInt(PREF_THEME, THEME_SYSTEM)}")
        return when (prefs.getInt(PREF_THEME, THEME_SYSTEM)) {
            THEME_SYSTEM -> R.style.Theme_Base_PodcastLite_Light
            THEME_LIGHT -> R.style.Theme_Base_PodcastLite_Light
            THEME_DARK -> R.style.Theme_Base_PodcastLite_Dark
            else -> throw IllegalStateException("Failed to get theme preference.")
        }
    }
}
