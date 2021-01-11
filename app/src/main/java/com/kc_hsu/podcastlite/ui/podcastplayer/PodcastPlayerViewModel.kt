package com.kc_hsu.podcastlite.ui.podcastplayer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.flac.PictureFrame
import com.google.android.exoplayer2.metadata.id3.ApicFrame
import com.kc_hsu.podcastlite.utils.Mp3PlayerStateHolder
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class PodcastPlayerViewModel : ViewModel(), KoinComponent {

    companion object {
        private const val PICTURE_TYPE_FRONT_COVER = 3
    }

    private var player: SimpleExoPlayer? = null
    val artwork = MutableLiveData<Bitmap>()

    fun initMp3Player(mp3Url: String, mp3PlayerStateHolder: Mp3PlayerStateHolder): SimpleExoPlayer {
        player = get { parametersOf(mp3Url, mp3PlayerStateHolder) }

        player?.apply {
            addListener(object : Player.EventListener {
                override fun onPlaybackStateChanged(state: Int) {
                    super.onPlaybackStateChanged(state)
                    if (state == Player.STATE_READY) {
                        val selections = currentTrackSelections
                        for (i in 0 until selections.length) {
                            val selection = selections.get(i)
                            selection?.let {
                                for (j in 0 until it.length()) {
                                    val metadata =
                                        it.getFormat(j).metadata
                                    if (metadata != null) {
                                        artwork.value = getArtwork(metadata)
                                        return
                                    }
                                }
                            }
                        }
                    }
                }
            })
        } ?: throw IllegalStateException("player is empty")

        return player!!
    }

    fun releaseMp3Player(): Mp3PlayerStateHolder {
        val holder = Mp3PlayerStateHolder()
        player?.let { player ->
            holder.playWhenReady = player.playWhenReady
            holder.currentWindow = player.currentWindowIndex
            holder.playbackPosition = player.currentPosition
            player.release()
        }
        player = null

        return holder
    }

    // Refer to https://github.com/google/ExoPlayer/blob/83477497c19959f4b33c3af57cc1e646fcc3848a/library/ui/src/main/java/com/google/android/exoplayer2/ui/PlayerView.java#L1396
    private fun getArtwork(metadata: Metadata): Bitmap? {
        var bitmap: Bitmap? = null
        for (i in 0 until metadata.length()) {
            val metadataEntry: Metadata.Entry =
                metadata.get(i)
            var pictureType: Int
            var bitmapData: ByteArray
            if (metadataEntry is ApicFrame) {
                bitmapData = metadataEntry.pictureData
                pictureType = metadataEntry.pictureType
            } else if (metadataEntry is PictureFrame) {
                bitmapData = metadataEntry.pictureData
                pictureType = metadataEntry.pictureType
            } else {
                continue
            }
            // Prefer the first front cover picture. If there aren't any, prefer the first picture.
            if (pictureType == PICTURE_TYPE_FRONT_COVER) {
                bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
            }
        }

        return bitmap
    }
}
