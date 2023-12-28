package com.roshanadke.weekwatch.domain.models

import android.os.Parcelable
import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class TrendingItem(
    val isAdult: Boolean?,
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
    val voteCount: Int?,
    var isFavourite: Boolean = false
) : Parcelable {

    fun toTrendingDataEntity(): TrendingDataEntity {
        return TrendingDataEntity(
            isAdult = isAdult,
            backdropPath = backdropPath,
            firstAirDate = firstAirDate,
            id = id,
            mediaType = mediaType,
            name = name,
            originalLanguage = originalLanguage,
            originalName = originalName,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            isVideo = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
