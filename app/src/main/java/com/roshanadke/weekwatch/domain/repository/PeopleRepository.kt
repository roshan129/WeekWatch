package com.roshanadke.weekwatch.domain.repository

import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.dto.people.PeopleListMainDto
import com.roshanadke.weekwatch.domain.models.people.PeopleListMainModel
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {

    fun getPopularPeopleList(page: Int): Flow<UiState<PeopleListMainModel>>

}