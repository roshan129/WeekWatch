package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import com.roshanadke.weekwatch.data.local.TvShowDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowDaoFake : TvShowDao {

    private var trendingDataEntityList = mutableListOf<TrendingDataEntity>()

    override suspend fun insert(item: TrendingDataEntity) {
        trendingDataEntityList.add(item)
    }

    override fun getAllRecords(): Flow<List<TrendingDataEntity>> {
        return flow {
            emit(trendingDataEntityList)
        }
    }

    override suspend fun delete(id: Int) {
        trendingDataEntityList.removeIf { it.id == id }
    }


}