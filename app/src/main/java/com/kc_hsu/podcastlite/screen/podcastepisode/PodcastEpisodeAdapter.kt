package com.kc_hsu.podcastlite.screen.podcastepisode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.data.responsebody.PodcastsBody
import com.kc_hsu.podcastlite.databinding.PodcastDetailListItemBinding
import timber.log.Timber

class PodcastEpisodeAdapter() : PagingDataAdapter<PodcastsBody.Episode, PodcastEpisodeAdapter.PodcastDetailViewHolder>(PodcastDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastDetailViewHolder {
        return PodcastDetailViewHolder(PodcastDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PodcastDetailViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) } ?: Timber.e("Failed to retrieve item at $position")
    }

    class PodcastDetailViewHolder(private val binding: PodcastDetailListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: PodcastsBody.Episode) {
            with(binding) {
                Glide.with(squareRoundedImageView)
                    .load(episode.thumbnail)
                    .placeholder(R.drawable.ic_mic_none)
                    .error(R.drawable.ic_mic_none)
                    .into(squareRoundedImageView)
                tvEpisodeTitle.text = episode.title
                tvEpisodeDescription.text = episode.description
            }
        }
    }

    object PodcastDiffCallback : DiffUtil.ItemCallback<PodcastsBody.Episode>() {
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
            return oldItem == newItem
        }
    }
}
