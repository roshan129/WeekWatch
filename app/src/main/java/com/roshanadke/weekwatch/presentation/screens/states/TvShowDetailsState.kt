package com.roshanadke.weekwatch.presentation.screens.states

import com.roshanadke.weekwatch.domain.models.TvShowDetails

data class TvShowDetailsState(
    val isLoading: Boolean = false,
    val showDetails: TvShowDetails = TvShowDetails()
)

