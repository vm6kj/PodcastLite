package com.kc_hsu.podcastlite.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kc_hsu.podcastlite.preferences.UserPreferences

open class BaseActivity : AppCompatActivity() {

    private var lastTheme: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lastTheme = UserPreferences.getTheme()
        setTheme(lastTheme)
    }

    override fun onStart() {
        super.onStart()
        if (lastTheme != UserPreferences.getTheme()) {
            recreate()
        }
    }
}
