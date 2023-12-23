package com.roshanadke.weekwatch.data.repository

import android.util.Log
import com.roshanadke.weekwatch.BuildConfig
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrendingShowRepositoryImpl(
    private val apiService: TrendingShowApiService
) : TrendingShowRepository {
    override fun getAllTrendingShows(key: String): Flow<TrendingResponseDto> = flow {
        try {
            val result = apiService.getAllTrending(key)
            emit(result)
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }


}