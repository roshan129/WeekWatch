package com.roshanadke.weekwatch.domain.use_case

import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.repository.DetailsRepository

class AddToFavouriteUseCase(
    private val repository: DetailsRepository
) {

    suspend operator fun invoke(item: TrendingItem?) {
        return repository.addToFavourites(item)
    }

}