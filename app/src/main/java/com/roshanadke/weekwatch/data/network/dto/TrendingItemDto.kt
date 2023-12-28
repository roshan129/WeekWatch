package com.roshanadke.weekwatch.data.network.dto

import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.google.gson.annotations.SerializedName

data class TrendingItemDto(
    @SerializedName("adult")
    val isAdult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int?,
    @SerializedName("media_type")
    val mediaType: String?,
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toTrendingItem(): TrendingItem {
        return TrendingItem(
            isAdult = isAdult,
            backdropPath = backdropPath,
            firstAirDate = firstAirDate,
            genreIds = genreIds,
            id = id,
            mediaType = mediaType,
            name = name,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
