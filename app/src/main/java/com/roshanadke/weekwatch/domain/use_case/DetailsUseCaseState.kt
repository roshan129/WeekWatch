package com.roshanadke.weekwatch.domain.use_case

data class DetailsUseCaseState(
    val tvShowDetailsUseCase: GetTvShowDetailsUseCase,
    val addToFavouriteUseCase: AddToFavouriteUseCase,
    val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    val similarShowsUseCase: GetSimilarShowsUseCase,

)