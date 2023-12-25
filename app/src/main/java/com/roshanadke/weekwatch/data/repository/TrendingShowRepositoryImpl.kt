package com.roshanadke.weekwatch.data.repository

import android.util.Log
import com.roshanadke.weekwatch.BuildConfig
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrendingShowRepositoryImpl(
    private val apiService: TrendingShowApiService
) : TrendingShowRepository {

    override fun getAllTrendingShows(): Flow<UiState<TrendingResponseDto>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getAllTrending()
            emit(UiState.Success(data = result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Error(message = e.localizedMessage))
        }
    }

}