package com.kc_hsu.podcastlite.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kc_hsu.podcastlite.data.responsebody.BestPodcastsBody
import com.kc_hsu.podcastlite.databinding.HomeBannerBinding
import com.kc_hsu.podcastlite.databinding.HomeCarouselListBinding
import com.kc_hsu.podcastlite.databinding.HomeHeaderBinding
import com.kc_hsu.podcastlite.databinding.HomeListLoadMoreBinding
import com.kc_hsu.podcastlite.screen.helpers.SpacesItemDecoration
import com.kc_hsu.podcastlite.utils.DebouncedClickListener
import com.youth.banner.transformer.ScaleInTransformer
import timber.log.Timber

class HomeAdapter internal constructor(private val podcastClickListener: PodcastClickListener, private val settingClickListener: SettingClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_FOOT = -1
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_BANNER = 1
        private const val VIEW_TYPE_HORIZONTAL_SCROLL = 2
        // TODO write a function to calculate offset for more complex UI
        private const val sectionOffset = 1
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
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(HomeHeaderBinding.inflate(layoutInflater, parent, false))
            }
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
            is HeaderViewHolder -> {
                holder.bind()
            }
            is BannerViewHolder -> {
                val adapter = ImageBannerAdapter(listOfBestPodcasts[position - sectionOffset])
                holder.bind(adapter)
            }
            is CarouselViewHolder -> {
                Timber.d("is CarouselViewHolder")
                val adapter = BestPodcastCarouselAdapter(listOfBestPodcasts[position - sectionOffset], podcastClickListener)
                holder.bind(adapter)
            }
            is LoadMoreViewHolder -> {
                holder.bind(loadingState)
            }
        }
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount: ${listOfBestPodcasts.size}")
        return 1 + listOfBestPodcasts.size // + if (loadingState == HomeDataState.Loading) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        // if (itemCount == position) {
        //     return VIEW_TYPE_FOOT
        // }
        return when (position) {
            VIEW_TYPE_HEADER -> {
                VIEW_TYPE_HEADER
            }
            VIEW_TYPE_BANNER -> {
                VIEW_TYPE_BANNER
            }
            VIEW_TYPE_HORIZONTAL_SCROLL -> {
                VIEW_TYPE_HORIZONTAL_SCROLL
            }
            // TODO add a view type list
            else -> {
                VIEW_TYPE_HORIZONTAL_SCROLL
            }
        }
    }

    inner class HeaderViewHolder(private val binding: HomeHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.ivSetting.setOnClickListener(object : DebouncedClickListener() {
                override fun onDebouncedClick(v: View) {
                    settingClickListener.onSettingClick()
                }
            })
        }
    }

    class BannerViewHolder(private val binding: HomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: ImageBannerAdapter) {
            binding.tvBannerTitle.text = adapter.bestPodcastsBody.name
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

    class CarouselViewHolder(private val binding: HomeCarouselListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: BestPodcastCarouselAdapter) {
            with(binding.rvCarousel) {
                setAdapter(adapter)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                if (itemDecorationCount == 0) {
                    addItemDecoration(SpacesItemDecoration())
                }
            }
            binding.tvListTitle.text = adapter.bestPodcastsBody.name
        }
    }

    class LoadMoreViewHolder(private val binding: HomeListLoadMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: HomeDataState) {
            binding.pbLoadMore.visibility = if (state == HomeDataState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
