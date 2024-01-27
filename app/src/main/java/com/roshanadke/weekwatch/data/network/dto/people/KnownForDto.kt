package com.roshanadke.weekwatch.data.network.dto.people

import com.google.gson.annotations.SerializedName
import com.roshanadke.weekwatch.domain.models.people.KnownForModel

data class KnownForDto(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("media_type") val mediaType: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) {
    fun toKnownForModel(): KnownForModel {
        return KnownForModel(
            adult = adult,
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
