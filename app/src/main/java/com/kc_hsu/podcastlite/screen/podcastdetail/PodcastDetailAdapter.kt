package com.kc_hsu.podcastlite.screen.podcastdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import com.kc_hsu.podcastlite.databinding.PodcastDetailListItemBinding

class PodcastDetailAdapter() : PagingDataAdapter<PodcastsBody.Episode, PodcastDetailAdapter.PodcastDetailViewHolder>(PodcastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastDetailViewHolder {
        return PodcastDetailViewHolder(PodcastDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PodcastDetailViewHolder, position: Int) {
        // holder.bind()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class PodcastDetailViewHolder(private val binding: PodcastDetailListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }

    class PodcastDiffCallback : DiffUtil.ItemCallback<PodcastsBody.Episode>() {
        override fun areItemsTheSame(
            oldItem: PodcastsBody.Episode,
            newItem: PodcastsBody.Episode
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PodcastsBody.Episode,
            newItem: PodcastsBody.Episode
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
