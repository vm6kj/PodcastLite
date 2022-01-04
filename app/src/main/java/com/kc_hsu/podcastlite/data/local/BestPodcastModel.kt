package com.kc_hsu.podcastlite.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// @Entity(
//    tableName = "bestpodcast_list",
//    primaryKeys = ["genre_id"],
//    foreignKeys = [ForeignKey(
//        entity = BestPodcastModel::class,
//        parentColumns = ["podcastid"],
//        childColumns = ["podcast_id"],
//        onDelete = ForeignKey.CASCADE,
//        onUpdate = ForeignKey.CASCADE
//    )]
// )
// data class BestPodcastListId(
//    @ColumnInfo(name = "podcast_id")
//    val podcastId: String,
//    @ColumnInfo(name = "genre_id")
//    val genre_id: Int
// )

@Entity(tableName = "bestpodcast")
data class BestPodcastModel(
    @PrimaryKey
    @ColumnInfo(name = "podcastid")
    val id: String,
    @ColumnInfo(name = "genreid")
    val genreId: Int,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "earliest_pub_date_ms")
    var earliestPubDateMs: Long?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "explicit_content")
    var explicitContent: Boolean?,
    @ColumnInfo(name = "image")
    var image: String?,
    @ColumnInfo(name = "is_claimed")
    var isClaimed: Boolean?,
    @ColumnInfo(name = "itunes_id")
    var itunesId: Int?,
    @ColumnInfo(name = "language")
    var language: String?,
    @ColumnInfo(name = "latest_pub_date_ms")
    var latestPubDateMs: Long?,
    @ColumnInfo(name = "listen_score")
    var listenScore: String?,
    @ColumnInfo(name = "publisher")
    var publisher: String?,
    @ColumnInfo(name = "thumbnail")
    var thumbnail: String?,
    @ColumnInfo(name = "title")
    var title: String?,
) : Serializable
