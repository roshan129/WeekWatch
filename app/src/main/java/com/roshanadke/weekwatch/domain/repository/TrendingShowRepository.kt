package com.roshanadke.weekwatch.domain.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import kotlinx.coroutines.flow.Flow

interface TrendingShowRepository {

    fun getAllTrendingShows(): Flow<UiState<TrendingResponseDto>>

    fun fetchSearchedShows(query: String): Flow<UiState<TrendingResponseDto>>


}