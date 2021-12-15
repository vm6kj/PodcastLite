package com.kc_hsu.podcastlite.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeBannerImageBinding
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class ImageBannerAdapter(bestPodcastsBody: BestPodcastsBody) : BannerAdapter<BestPodcastsBody.Podcast, ImageBannerAdapter.ImageBannerViewHolder>(bestPodcastsBody.podcasts) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageBannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = HomeBannerImageBinding.inflate(layoutInflater, parent, false)
        BannerUtils.setBannerRound(binding.ivBanner, 20f)
        return ImageBannerViewHolder(binding)
    }

    override fun onBindView(
        holder: ImageBannerViewHolder?,
        data: BestPodcastsBody.Podcast?,
        position: Int,
        size: Int
    ) {
        holder?.bind(data?.image)
    }

    inner class ImageBannerViewHolder(private val binding: HomeBannerImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String?) {
            Glide.with(itemView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_mic_none)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivBanner)
        }
    }
}
