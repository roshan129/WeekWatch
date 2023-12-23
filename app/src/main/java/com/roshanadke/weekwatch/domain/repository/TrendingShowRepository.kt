package com.roshanadke.weekwatch.domain.repository

import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import kotlinx.coroutines.flow.Flow

interface TrendingShowRepository {

    fun getAllTrendingShows(key: String): Flow<TrendingResponseDto>

}