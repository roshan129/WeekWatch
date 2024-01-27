package com.roshanadke.weekwatch.domain.models.people

import com.google.gson.annotations.SerializedName

data class KnownForModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>?,
    val id: Int?,
    val mediaType: String?,
    val name: String?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalName: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
)
