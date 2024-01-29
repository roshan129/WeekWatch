package com.roshanadke.weekwatch.domain.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.domain.models.people.PersonDetailsModel
import kotlinx.coroutines.flow.Flow

interface PersonDetailsRepository {

    fun getPersonDetails(id: Int): Flow<UiState<PersonDetailsModel>>

}