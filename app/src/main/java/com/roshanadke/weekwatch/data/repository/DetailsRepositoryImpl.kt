package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsRepositoryImpl(
    private val apiService: TrendingShowApiService
) : DetailsRepository {
    override fun getTvShowDetails(id: String): Flow<UiState<TvShowDetails>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getTvShowDetails(id).toTvShowDetails()
            emit(UiState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Error(message = e.localizedMessage))
        }
    }

    override fun getSimilarShows(id: String): Flow<UiState<TrendingResponseDto>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getSimilarShows(id)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Error(message = e.localizedMessage))
        }
    }


}