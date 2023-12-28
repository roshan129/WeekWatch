package com.roshanadke.weekwatch.data.utils

import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import com.roshanadke.weekwatch.data.network.dto.TrendingItemDto
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.data.network.dto.tv_details.TvShowDetailsMainDto


fun getTrendingResponseDto(list: List<TrendingItemDto>): TrendingResponseDto {
    return TrendingResponseDto(
        page = null,
        trendingItemDtoList = list,
        totalPages = null,
        totalResults = null
    )
}

fun getTrendingItemDto(id: Int, title: String = "Dummy Movie Title"): TrendingItemDto {
    return TrendingItemDto(
        isAdult = false,
        backdropPath = "dummyBackdropPath",
        firstAirDate = "2023-01-01",
        genreIds = listOf(1, 2, 3),
        id = id,
        mediaType = "movie",
        name = "Dummy Movie",
        originCountry = listOf("US", "CA"),
        originalLanguage = "en",
        originalName = "Dummy Original Name",
        originalTitle = "Dummy Original Title",
        overview = "This is a dummy overview",
        popularity = 7.8,
        posterPath = "/dummyPosterPath",
        releaseDate = "2023-01-01",
        title = title,
        video = true,
        voteAverage = 8.5,
        voteCount = 100
    )
}

fun getTvShowDetailsMainDto(id: Int): TvShowDetailsMainDto {
    return TvShowDetailsMainDto(
        isAdult = false,
        backdropPath = "backdropPath.jpg",
        episodeRunTimeList = listOf(),
        firstAirDate = "1985-09-29",
        homepage = "dummyHomepage",
        id = id,
        inProduction = false,
        languageList = listOf(),
        lastAirDate = "1995-09-29",
        name = "Rodger Savage",
        numberOfEpisodes = 2166,
        numberOfSeasons = 2968,
        originCountryList = listOf(),
        originalLanguage = "dummyLanguage",
        originalName = "Sheena Hopper",
        overview = "dummyOverview",
        popularity = 4.5,
        posterPath = "dummyPosterPath",
        seasonList = listOf(),
        status = "dummyStatus",
        tagline = "dummyTagline",
        type = "justo",
        voteAverage = 6.7,
        voteCount = 8447
    )
}

fun getTrendingDataEntity(id: Int): TrendingDataEntity {
    return TrendingDataEntity(
        isAdult = false,
        backdropPath = "dummyBackdropPath.jpg",
        firstAirDate = "dummyFirstAirDate",
        id = id,
        mediaType = "dummyMediaType",
        name = "dummyName",
        originalLanguage = "dummyOriginalLanguage",
        originalName = "dummyOriginalName",
        originalTitle = "dummyOriginalTitle",
        overview = "dummyOverview",
        popularity = 4.5,
        posterPath = "dummyPosterPath",
        releaseDate = "dummyReleaseDate",
        title = "dummyTitle",
        isVideo = true,
        voteAverage = 9.8,
        voteCount = 100
    )
}