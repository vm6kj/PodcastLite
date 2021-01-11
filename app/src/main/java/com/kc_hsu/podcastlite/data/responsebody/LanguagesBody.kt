package com.kc_hsu.podcastlite.data.responsebody

import com.google.gson.annotations.SerializedName

data class LanguagesBody(
    @SerializedName("languages")
    var languages: List<String?>?
)
