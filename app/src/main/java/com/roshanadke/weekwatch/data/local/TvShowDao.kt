package com.roshanadke.weekwatch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TrendingDataEntity)

    @Query("SELECT * FROM TrendingDataEntity")
    fun getAllRecords(): Flow<List<TrendingDataEntity>>

    @Query("DELETE FROM TrendingDataEntity WHERE id = :id")
    suspend fun delete(id: Int)

}