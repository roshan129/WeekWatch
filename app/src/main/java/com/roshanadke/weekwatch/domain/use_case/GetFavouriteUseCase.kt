package com.roshanadke.weekwatch.domain.use_case

import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteUseCase(
    private val repository: TrendingShowRepository
) {

    operator fun invoke(): Flow<List<TrendingDataEntity>> {
        return repository.getAllLocalRecords()
    }

}