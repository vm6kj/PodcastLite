package com.kc_hsu.podcastlite.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeBannerBinding
import com.kc_hsu.podcastlite.databinding.HomeCarouselListBinding
import com.kc_hsu.podcastlite.databinding.HomeListLoadMoreBinding
import com.kc_hsu.podcastlite.ui.helpers.SpacesItemDecoration
import com.youth.banner.transformer.ScaleInTransformer
import timber.log.Timber

class HomeAdapter internal constructor(private val listener: PodcastClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_FOOT = -1
        private const val VIEW_TYPE_BANNER = 0
        private const val VIEW_TYPE_HORIZONTAL_SCROLL = 1
    }

    private var loadingState: HomeDataState = HomeDataState.Idle

    private var listOfBestPodcasts = mutableListOf<BestPodcastsBody>()
    fun updateData(bestPodcasts: List<BestPodcastsBody>) {
        bestPodcasts.subList(listOfBestPodcasts.size, bestPodcasts.size).forEachIndexed { index, bestPodcastsBody ->
            listOfBestPodcasts.add(bestPodcastsBody)
            notifyItemInserted(listOfBestPodcasts.size)
        }
    }

    fun loadMore(shouldLoadMore: Boolean) {
        loadingState = if (shouldLoadMore) {
            HomeDataState.Loading
        } else {
            HomeDataState.Idle
        }
        // notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.d("onCreateViewHolderQQ")
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_BANNER -> {
                BannerViewHolder(HomeBannerBinding.inflate(layoutInflater, parent, false))
            }
            VIEW_TYPE_FOOT -> {
                LoadMoreViewHolder(HomeListLoadMoreBinding.inflate(layoutInflater, parent, false))
            }
            else -> CarouselViewHolder(HomeCarouselListBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                val adapter = ImageBannerAdapter(listOfBestPodcasts[position])
                holder.bind(adapter)
            }
            is CarouselViewHolder -> {
                Timber.d("is CarouselViewHolder")
                val adapter = BestPodcastCarouselAdapter(listOfBestPodcasts[position], listener)
                holder.bind(adapter)
            }
            is LoadMoreViewHolder -> {
                holder.bind(loadingState)
            }
        }
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount: ${listOfBestPodcasts.size}")
        return listOfBestPodcasts.size // + if (loadingState == HomeDataState.Loading) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        // if (itemCount == position) {
        //     return VIEW_TYPE_FOOT
        // }
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
            binding.tvBannerTitle.text = listOfBestPodcasts[layoutPosition].name
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

    inner class CarouselViewHolder(private val binding: HomeCarouselListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: BestPodcastCarouselAdapter) {
            with(binding.rvCarousel) {
                setAdapter(adapter)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                if (itemDecorationCount == 0) {
                    addItemDecoration(SpacesItemDecoration())
                }
            }
            binding.tvListTitle.text = listOfBestPodcasts[layoutPosition].name
        }
    }

    inner class LoadMoreViewHolder(private val binding: HomeListLoadMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: HomeDataState) {
            binding.pbLoadMore.visibility = if (state == HomeDataState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
