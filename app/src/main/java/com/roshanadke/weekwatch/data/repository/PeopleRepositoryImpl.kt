package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.PeopleApiService
import com.roshanadke.weekwatch.data.network.dto.people.PeopleListMainDto
import com.roshanadke.weekwatch.domain.models.people.PeopleListMainModel
import com.roshanadke.weekwatch.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PeopleRepositoryImpl(
    private val apiService: PeopleApiService
) : PeopleRepository {

    override fun getPopularPeopleList(page: Int): Flow<UiState<PeopleListMainModel>> = flow {
        try {
            emit(UiState.Loading())
            val result = apiService.getPopularPersonsList(page).toPeopleList()
            emit(UiState.Success(data = result))
        }catch (e: Exception) {
            emit(UiState.Error(data = null, message = e.message))
        }
    }

}