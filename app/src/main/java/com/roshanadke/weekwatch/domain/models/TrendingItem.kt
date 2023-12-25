package com.roshanadke.weekwatch.domain.models

import android.os.Parcelable
import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class TrendingItem(
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
    val vote_count: Int?,
    var isFavourite: Boolean = false
): Parcelable {

    fun toTrendingDataEntity(): TrendingDataEntity {
        return TrendingDataEntity(
            adult = adult,
            backdrop_path = backdrop_path,
            first_air_date = first_air_date,
            id = id,
            media_type = media_type,
            name = name,
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
