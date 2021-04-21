package com.kc_hsu.podcastlite.utils

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kc_hsu.podcastlite.customview.SquareRoundedImageView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder", "error"], requireAll = false)
    fun loadPodcastCover(squareRoundedImageView: SquareRoundedImageView, imageUrl: String?, placeholder: Drawable?, error: Drawable?) {
        Glide.with(squareRoundedImageView)
            .load(imageUrl)
            .placeholder(placeholder)
            .error(error)
            .into(squareRoundedImageView)
    }
}
