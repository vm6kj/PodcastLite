package com.kc_hsu.podcastlite.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Activity.openFragment(fragment: Fragment, addToBackStack: Boolean) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction().apply {
        // add
    }
}
