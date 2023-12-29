package com.roshanadke.weekwatch.domain.use_case

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow

class GetSimilarShowsUseCase(
    private val repository: DetailsRepository
) {

    operator fun invoke(id: String): Flow<UiState<TrendingResponseDto>> {
        return repository.getSimilarShows(id)
    }

}