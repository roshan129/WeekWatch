package com.roshanadke.weekwatch.presentation.screens.states

import com.roshanadke.weekwatch.domain.models.people.PersonDetailsModel

data class PersonDetailsState(
    val isLoading: Boolean = false,
    val personDetails: PersonDetailsModel? = null
)
