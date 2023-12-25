package com.roshanadke.weekwatch.presentation.screens

import com.roshanadke.weekwatch.domain.models.TvShowDetails

data class TvShowDetailsState(
    val isLoading: Boolean = false,
    val showDetails: TvShowDetails = TvShowDetails()
)

