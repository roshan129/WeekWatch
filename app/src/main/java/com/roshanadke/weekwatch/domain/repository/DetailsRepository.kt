package com.roshanadke.weekwatch.domain.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun getTvShowDetails(id: String): Flow<UiState<TvShowDetails>>

    fun getSimilarShows(id: String): Flow<UiState<TrendingResponseDto>>

}