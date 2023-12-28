package com.roshanadke.weekwatch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [TrendingDataEntity::class],
    version = 2
)
abstract class TvShowDatabase : RoomDatabase() {

    abstract fun getTvShowDao(): TvShowDao

}