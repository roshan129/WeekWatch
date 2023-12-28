package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import com.roshanadke.weekwatch.data.local.TvShowDao
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TrendingShowRepositoryImpl(
    private val apiService: TrendingShowApiService,
    private val dao: TvShowDao
) : TrendingShowRepository {

    override fun getAllTrendingShows(): Flow<UiState<TrendingResponseDto>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getAllTrending()
            emit(UiState.Success(data = result))
        } catch (e: IOException) {
            emit(UiState.Error(data = null, message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(UiState.Error(data = null, message = e.localizedMessage))
        }
    }

    override fun fetchSearchedShows(query: String): Flow<UiState<TrendingResponseDto>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.fetchSearchedShows(query)
            emit(UiState.Success(result))
        } catch (e: IOException) {
            emit(UiState.Error(data = null, message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(UiState.Error(data = null, message = e.localizedMessage))
        }
    }

    override fun getAllLocalRecords(): Flow<List<TrendingDataEntity>> {
        return dao.getAllRecords()
    }

}