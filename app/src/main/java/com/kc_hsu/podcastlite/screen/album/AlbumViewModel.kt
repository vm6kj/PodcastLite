package com.kc_hsu.podcastlite.screen.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlbumViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Album Fragment"
    }
    val text: LiveData<String> = _text
}
