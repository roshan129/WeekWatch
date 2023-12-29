package com.roshanadke.weekwatch.domain.use_case

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingShowUseCase(
    private val repository: TrendingShowRepository
) {

    operator fun invoke(): Flow<UiState<TrendingResponseDto>> {
        return repository.getAllTrendingShows()
    }

}