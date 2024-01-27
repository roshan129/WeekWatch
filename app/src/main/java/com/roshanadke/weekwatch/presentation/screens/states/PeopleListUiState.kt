package com.roshanadke.weekwatch.presentation.screens.states

import com.roshanadke.weekwatch.data.network.dto.people.PeopleListMainDto
import com.roshanadke.weekwatch.domain.models.people.PeopleListMainModel


data class PeopleListUiState(
    val peopleListMainDto: PeopleListMainModel? = null,
    val isLoading: Boolean = false
)