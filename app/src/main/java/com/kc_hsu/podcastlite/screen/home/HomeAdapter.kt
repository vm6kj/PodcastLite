package com.kc_hsu.podcastlite.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.databinding.HomeBannerBinding
import com.kc_hsu.podcastlite.databinding.HomeCarouselListBinding
import com.kc_hsu.podcastlite.databinding.HomeFooterBinding
import com.kc_hsu.podcastlite.databinding.HomeHeaderBinding
import com.kc_hsu.podcastlite.screen.helpers.CarouselSpacesItemDecoration
import com.kc_hsu.podcastlite.utils.DebouncedClickListener
import com.youth.banner.transformer.ScaleInTransformer

class HomeAdapter internal constructor(
    private val podcastClickListener: PodcastClickListener,
    private val settingClickListener: SettingClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        /**
         * View Structure:
         *
         * -----HEADER VIEW-----    => Fixed view, always show
         * -----BANNER VIEW-----    => listOfBestPodcasts[0]
         * ----CAROUSEL VIEW----    => listOfBestPodcasts[1 to itemCount-2]
         * ------FOOT VIEW------    => Fixed view, shown if (loadingState == HomeDataState.Loading), hidden otherwise
         * */
        private enum class ViewType {
            VIEW_TYPE_HEADER, VIEW_TYPE_BANNER, VIEW_TYPE_CAROUSEL, VIEW_TYPE_FOOTER
        }

        // BANNER VIEW and CAROUSEL VIEW are always below HEADER VIEW
        private const val VIEW_SECTION_OFFSET = 1
    }

    private var loadingState: HomeDataState = HomeDataState.Idle

    private var listOfBestPodcasts = mutableListOf<List<BestPodcastModel>>()
    // TODO Use data provider for better architecture
    fun updateData(bestPodcasts: List<BestPodcastModel>) {
        listOfBestPodcasts.add(bestPodcasts)
        notifyItemInserted(listOfBestPodcasts.size)
    }

    fun loadMore(shouldLoadMore: Boolean) {
        loadingState = if (shouldLoadMore) {
            HomeDataState.Loading
        } else {
            HomeDataState.Idle
        }
        // Always in the bottom
        notifyItemChanged(listOfBestPodcasts.size + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.home_header -> {
                HeaderViewHolder(HomeHeaderBinding.inflate(layoutInflater, parent, false))
            }
            R.layout.home_banner -> {
                BannerViewHolder(HomeBannerBinding.inflate(layoutInflater, parent, false))
            }
            R.layout.home_carousel_list -> {
                CarouselViewHolder(HomeCarouselListBinding.inflate(layoutInflater, parent, false))
            }
            R.layout.home_footer -> {
                LoadMoreViewHolder(HomeFooterBinding.inflate(layoutInflater, parent, false))
            }
            else -> throw IllegalStateException("Illegal viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()
            }
            is BannerViewHolder -> {
                val adapter = ImageBannerAdapter(listOfBestPodcasts[position - VIEW_SECTION_OFFSET])
                holder.bind(adapter)
            }
            is CarouselViewHolder -> {
                val adapter = BestPodcastCarouselAdapter(
                    listOfBestPodcasts[position - VIEW_SECTION_OFFSET],
                    podcastClickListener
                )
                holder.bind(adapter)
            }
            is LoadMoreViewHolder -> {
                holder.bind(loadingState)
            }
        }
    }

    override fun getItemCount(): Int {
        // 2: HEADER VIEW and FOOT VIEW
        return 2 + listOfBestPodcasts.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (getViewTypeAtPosition(position)) {
            ViewType.VIEW_TYPE_HEADER -> R.layout.home_header
            ViewType.VIEW_TYPE_BANNER -> R.layout.home_banner
            ViewType.VIEW_TYPE_CAROUSEL -> R.layout.home_carousel_list
            ViewType.VIEW_TYPE_FOOTER -> R.layout.home_footer
        }
    }

    private fun getViewTypeAtPosition(position: Int): ViewType {
        if (listOfBestPodcasts.isEmpty()) {
            return if (position == 0) {
                ViewType.VIEW_TYPE_HEADER
            } else {
                ViewType.VIEW_TYPE_FOOTER
            }
        } else {
            return when (position) {
                0 -> {
                    ViewType.VIEW_TYPE_HEADER
                }
                1 -> {
                    ViewType.VIEW_TYPE_BANNER
                }
                in 2 until itemCount - 1 -> {
                    ViewType.VIEW_TYPE_CAROUSEL
                }
                itemCount - 1 -> {
                    ViewType.VIEW_TYPE_FOOTER
                }
                else -> {
                    throw IllegalStateException("Illegal position: $position")
                }
            }
        }
    }

    inner class HeaderViewHolder(private val binding: HomeHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
            binding.tvBannerTitle.text = adapter.bestPodcasts[0].genre
            binding.bannerBestPodcasts.apply {
                setAdapter(adapter)
                setIndicator(BannerIndicator(context))
                addPageTransformer(ScaleInTransformer())
                setOnBannerListener { data, position ->
                    Snackbar.make(
                        this,
                        "Banner clicked on ${(data as BestPodcastModel).title} ($position)",
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
                    val itemSpacing = resources.getDimensionPixelSize(R.dimen.carousel_spacing)
                    addItemDecoration(CarouselSpacesItemDecoration(itemSpacing))
                }
            }
            binding.tvListTitle.text = adapter.bestPodcasts[0].genre
        }
    }

    class LoadMoreViewHolder(private val binding: HomeFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(state: HomeDataState) {
            binding.pbLoadMore.visibility = if (state == HomeDataState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
