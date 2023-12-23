package com.roshanadke.weekwatch.data.network.dto

import com.roshanadke.weekwatch.domain.models.TrendingItem

data class TrendingItemDto(
    val adult: Boolean?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val media_type: String?,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    fun toTrendingItem(): TrendingItem {
        return TrendingItem(
            adult = adult,
            backdrop_path = backdrop_path,
            first_air_date = first_air_date,
            genre_ids = genre_ids,
            id = id,
            media_type = media_type,
            name = name,
            origin_country = origin_country,
            original_language = original_language,
            original_name = original_name,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            release_date = release_date,
            title = title,
            video = video,
            vote_average = vote_average,
            vote_count = vote_count

        )
    }
}
