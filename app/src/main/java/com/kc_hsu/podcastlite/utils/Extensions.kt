package com.kc_hsu.podcastlite.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kc_hsu.podcastlite.R

fun Activity.openFragment(fragment: Fragment, addToBackStack: Boolean) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction().apply {
        add(R.id.home_fragment_container, fragment)
        if (addToBackStack) addToBackStack(fragment.toString())
        commitAllowingStateLoss()
    }
}

fun Fragment.openFragment(fragment: Fragment, addToBackStack: Boolean) {
    parentFragmentManager.beginTransaction().apply {
        add(R.id.home_fragment_container, fragment)
        if (addToBackStack) addToBackStack(fragment.toString())
        commitAllowingStateLoss()
    }
}

fun Fragment.replaceFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction().apply {
        replace(R.id.home_fragment_container, fragment)
        addToBackStack(null)
        commit()
    }
}
