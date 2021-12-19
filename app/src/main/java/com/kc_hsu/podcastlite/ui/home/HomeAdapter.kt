package com.kc_hsu.podcastlite.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeBannerBinding
import com.kc_hsu.podcastlite.databinding.HomeCarouselListBinding
import com.youth.banner.transformer.ScaleInTransformer

class HomeAdapter internal constructor(private val bestPodcastsBody: ArrayList<BestPodcastsBody>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_BANNER = 0
        private const val VIEW_TYPE_HORIZONTAL_SCROLL = 1
    }

    fun updateData(bestPodcasts: List<BestPodcastsBody>) {
//        listOfBestPodcasts.add(bestPodcasts)
        bestPodcastsBody.addAll(bestPodcasts)
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_BANNER -> {
                BannerViewHolder(HomeBannerBinding.inflate(layoutInflater, parent, false))
            }
            else -> CarouselViewHolder(HomeCarouselListBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                val adapter = ImageBannerAdapter(bestPodcastsBody[position])
                holder.bind(adapter)
            }
            is CarouselViewHolder -> {
                val adapter = BestPodcastCarouselAdapter(bestPodcastsBody[position])
                holder.bind(adapter)
            }
        }
    }

    override fun getItemCount(): Int {
        return bestPodcastsBody.size
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
                addPageTransformer(ScaleInTransformer())
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

    inner class CarouselViewHolder(private val binding: HomeCarouselListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: BestPodcastCarouselAdapter) {
            with(binding.rvCarousel) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                setAdapter(adapter)
            }
            binding.tvListTitle.text = bestPodcastsBody[layoutPosition].name
        }
    }
}
