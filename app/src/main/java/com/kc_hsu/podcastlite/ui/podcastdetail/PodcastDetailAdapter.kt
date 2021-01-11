package com.kc_hsu.podcastlite.ui.podcastdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kc_hsu.podcastlite.data.responsebody.PodcastDetailBody
import com.kc_hsu.podcastlite.databinding.ListItemCastDetailBinding

class PodcastDetailAdapter(private val podcastDetailViewModel: PodcastDetailViewModel) :
    PagingDataAdapter<PodcastDetailBody.Data.Collection.ContentFeed, PodcastDetailAdapter.PodcastDetailViewHolder>(
        diffCallback
    ) {

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<PodcastDetailBody.Data.Collection.ContentFeed>() {
                override fun areItemsTheSame(
                    oldItem: PodcastDetailBody.Data.Collection.ContentFeed,
                    newItem: PodcastDetailBody.Data.Collection.ContentFeed
                ): Boolean {
                    return oldItem.contentUrl == newItem.contentUrl
                }

                override fun areContentsTheSame(
                    oldItem: PodcastDetailBody.Data.Collection.ContentFeed,
                    newItem: PodcastDetailBody.Data.Collection.ContentFeed
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    class PodcastDetailViewHolder(private val itemCastDetailBinding: ListItemCastDetailBinding) :
        RecyclerView.ViewHolder(itemCastDetailBinding.root) {
        fun bindTo(
            viewModel: PodcastDetailViewModel,
            podcastFeed: PodcastDetailBody.Data.Collection.ContentFeed
        ) {
            itemCastDetailBinding.viewmodel = viewModel
            itemCastDetailBinding.podcastfeed = podcastFeed
            itemCastDetailBinding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: PodcastDetailViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(podcastDetailViewModel, it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCastDetailBinding.inflate(layoutInflater)
        return PodcastDetailViewHolder(binding)
    }
}
