package com.kc_hsu.podcastlite.ui.podcastlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kc_hsu.podcastlite.data.responsebody.PodcastBody
import com.kc_hsu.podcastlite.databinding.ListItemCastCoverBinding

class PodcastListAdapter(private val podcastListViewModel: PodcastListViewModel) :
    PagingDataAdapter<PodcastBody.Data.Podcast, PodcastListAdapter.PodcastViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PodcastBody.Data.Podcast>() {
            override fun areItemsTheSame(
                oldItem: PodcastBody.Data.Podcast,
                newItem: PodcastBody.Data.Podcast
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PodcastBody.Data.Podcast,
                newItem: PodcastBody.Data.Podcast
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class PodcastViewHolder(private val itemCastCoverBinding: ListItemCastCoverBinding) :
        RecyclerView.ViewHolder(itemCastCoverBinding.root) {
        fun bindTo(viewModel: PodcastListViewModel, podcast: PodcastBody.Data.Podcast) {
            itemCastCoverBinding.viewmodel = viewModel
            itemCastCoverBinding.podcast = podcast
            itemCastCoverBinding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(podcastListViewModel, it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCastCoverBinding.inflate(layoutInflater, parent, false)
        return PodcastViewHolder(binding)
    }
}
