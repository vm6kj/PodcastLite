package com.kc_hsu.podcastlite.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BestPodcastListId::class, BestPodcastModel::class], version = 1, exportSchema = true)
abstract class PodcastDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
}
