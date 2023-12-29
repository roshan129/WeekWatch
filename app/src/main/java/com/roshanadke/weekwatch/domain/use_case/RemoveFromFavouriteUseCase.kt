package com.roshanadke.weekwatch.domain.use_case

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow

class RemoveFromFavouriteUseCase(
    private val repository: DetailsRepository
) {

    suspend operator fun invoke(id: Int?) {
        return repository.removeFromFavourites(id)
    }

}