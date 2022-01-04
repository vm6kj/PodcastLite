package com.kc_hsu.podcastlite.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.data.local.BestPodcastModel
import com.kc_hsu.podcastlite.databinding.HomeItemBestPodcastBinding
import com.kc_hsu.podcastlite.utils.DebouncedClickListener

class BestPodcastCarouselAdapter(val bestPodcasts: List<BestPodcastModel>, private val listener: PodcastClickListener) : RecyclerView.Adapter<BestPodcastCarouselAdapter.BestPodcastCarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestPodcastCarouselViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeItemBestPodcastBinding.inflate(layoutInflater, parent, false)
        return BestPodcastCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestPodcastCarouselViewHolder, position: Int) {
        holder.bind(bestPodcasts[position])
    }

    override fun getItemCount(): Int = bestPodcasts.size

    inner class BestPodcastCarouselViewHolder(private val binding: HomeItemBestPodcastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(podcast: BestPodcastModel) {
            with(binding) {
                cvBestPodcast.setOnClickListener(object : DebouncedClickListener() {
                    override fun onDebouncedClick(v: View) {
                        listener.onPodcastClick(podcast)
                    }
                })
                Glide.with(itemView)
                    .load(podcast.image)
                    .placeholder(R.drawable.ic_mic_none)
                    .into(ivPodcastImage)
                tvPodcastName.text = podcast.title
            }
        }
    }
}
