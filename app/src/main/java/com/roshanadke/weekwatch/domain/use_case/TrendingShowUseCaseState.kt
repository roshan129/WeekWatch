package com.roshanadke.weekwatch.domain.use_case

data class TrendingShowUseCaseState(
    val trendingShowUseCase: GetTrendingShowUseCase,
    val getFavouriteUseCase: GetFavouriteUseCase,
    val searchedShowUseCase: GetSearchedShowUseCase
)
