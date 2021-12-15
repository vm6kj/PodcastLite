package com.kc_hsu.podcastlite.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeBannerBinding
import com.youth.banner.indicator.BaseIndicator

class HomeAdapter internal constructor(private val bestPodcastsBody: BestPodcastsBody) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_BANNER = 0
        private const val VIEW_TYPE_HORIZONTAL_SCROLL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_BANNER -> {
                BannerViewHolder(HomeBannerBinding.inflate(layoutInflater, parent, false))
            }
            else -> BannerViewHolder(HomeBannerBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                val adapter = ImageBannerAdapter(bestPodcastsBody)
                holder.bind(adapter)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            VIEW_TYPE_BANNER -> {
                VIEW_TYPE_BANNER
            }
            VIEW_TYPE_HORIZONTAL_SCROLL -> {
                VIEW_TYPE_HORIZONTAL_SCROLL
            }
            else -> {
                VIEW_TYPE_HORIZONTAL_SCROLL
            }
        }
    }

    inner class BannerViewHolder(private val binding: HomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: ImageBannerAdapter) {
            binding.bannerBestPodcasts.apply {
                setAdapter(adapter)
                setIndicator(BannerIndicator(context))
                setOnBannerListener { data, position ->
                    Snackbar.make(
                        this,
                        "Banner clicked on ${(data as BestPodcastsBody.Podcast).title} ($position)",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
