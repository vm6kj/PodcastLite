package com.kc_hsu.podcastlite.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PodcastDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPodcastList(podcast: BestPodcastModel): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePodcastList(podcast: BestPodcastModel)

    @Transaction
    fun upsertMovieListResponse(podcast: BestPodcastModel) {
        if (insertPodcastList(podcast) == -1L) {
            updatePodcastList(podcast)
        }
    }

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertGenreId(listId: BestPodcastListId)

    @Transaction
    fun upsertBestPodcastList(genreId: Int, list: List<BestPodcastModel>) {
        list.forEachIndexed { index, podcast ->
            upsertMovieListResponse(podcast)
//            insertGenreId(BestPodcastListId(podcastId = podcast.id, genre_id = genreId))
        }
    }

    @Query("SELECT * FROM bestpodcast where genreid = :genreId")
    fun queryBestPodcastList(genreId: Int): List<BestPodcastModel>

//    @Transaction
//    fun upsertBestPodcastList(genreId: Int, list: List<BestPodcastModel>) {
//        list.forEachIndexed { index, podcast ->
//            upsertMovieListResponse(podcast)
//            insertGenreId(BestPodcastListId(podcastId = podcast.id, genre_id = genreId))
//        }
//    }

//    @Query("SELECT * FROM bestpodcast INNER JOIN bestpodcast_list ON bestpodcast.genreId = bestpodcast_list.genre_id WHERE bestpodcast_list.genre_id == :genreId")
//    fun queryBestPodcastList(genreId: Int): List<BestPodcastModel>
}
