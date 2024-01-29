package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.PeopleApiService
import com.roshanadke.weekwatch.domain.models.people.PersonDetailsModel
import com.roshanadke.weekwatch.domain.repository.PersonDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonDetailsRepositoryImpl(
    private val apiService: PeopleApiService
): PersonDetailsRepository {
    override fun getPersonDetails(id: Int): Flow<UiState<PersonDetailsModel>> = flow{
        try {
            emit(UiState.Loading())
            val result = apiService.getPersonDetails(id)
            emit(UiState.Success(data = result.toPersonDetailsModel()))

        }catch (e: Exception) {
            emit(UiState.Error(data = null, message = e.message))
        }
    }
}