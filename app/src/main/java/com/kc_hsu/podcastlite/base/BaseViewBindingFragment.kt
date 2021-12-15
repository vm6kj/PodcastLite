package com.kc_hsu.podcastlite.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseViewBindingFragment<B : ViewBinding>(private val inflate: Inflate<B>) :
    Fragment() {
    protected var binding by AutoClearedValue<B>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // https://developer.android.com/topic/libraries/view-binding
        binding = inflate.invoke(inflater, container, false)
        return binding.root
    }
}
