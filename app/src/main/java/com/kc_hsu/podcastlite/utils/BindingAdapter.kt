package com.kc_hsu.podcastlite.utils

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kc_hsu.podcastlite.customview.SquareImageView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder", "error"])
    fun loadPodcastCover(squareImageView: SquareImageView, imageUrl: String?, placeholder: Drawable?, error: Drawable?) {
        Glide.with(squareImageView)
            .load(imageUrl)
            .placeholder(placeholder)
            .error(error)
            .into(squareImageView)
    }
}
