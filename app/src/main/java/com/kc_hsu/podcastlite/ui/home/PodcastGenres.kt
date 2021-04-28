package com.kc_hsu.podcastlite.ui.home

import androidx.annotation.StringRes
import com.kc_hsu.podcastlite.R

enum class PodcastGenres(val genreId: Int, @StringRes val stringRes: Int) {
    PERSONAL_FINANCE(144, R.string.genre_personal_finance),
    LOCALLY_FOCUSED(151, R.string.genre_locally_focused),
    SCIENCE(107, R.string.genre_science),
    MUSIC(134, R.string.genre_music),
    NEWS(99, R.string.genre_news),
    RELIGION_AND_SPIRITUALITY(69, R.string.genre_religion_and_spirituality),
    BUSINESS(93, R.string.genre_business),
    COMEDY(133, R.string.genre_comedy),
    SOCIETY_AND_CULTURE(122, R.string.genre_society_and_culture),
    FICTION(168, R.string.genre_fiction)
}
