package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.local.TvShowDao
import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class DetailsRepositoryImpl(
    private val apiService: TrendingShowApiService,
    private val dao: TvShowDao
) : DetailsRepository {
    override fun getTvShowDetails(id: String): Flow<UiState<TvShowDetails>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getTvShowDetails(id).toTvShowDetails()
            emit(UiState.Success(result))
        } catch (e: IOException) {
            emit(UiState.Error(data = null, message = "Please check your internet connection"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Error(data = null, message = e.localizedMessage))
        }
    }

    override fun getSimilarShows(id: String): Flow<UiState<TrendingResponseDto>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getSimilarShows(id)
            emit(UiState.Success(result))
        } catch (e: IOException) {
            emit(UiState.Error(data = null, message = "Please check your internet connection"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Error(data = null, message = e.localizedMessage))
        }
    }

    override suspend fun addToFavourites(item: TrendingItem?) {
        item?.let {
            val entity = item.toTrendingDataEntity()
            dao.insert(entity)
        }
    }

    override suspend fun removeFromFavourites(id: Int?) {
        id?.let {
            dao.delete(id)
        }
    }


}