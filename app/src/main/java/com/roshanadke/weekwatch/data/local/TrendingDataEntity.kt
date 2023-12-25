package com.roshanadke.weekwatch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TrendingDataEntity(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val first_air_date: String? = null,
    val id: Int? = null,
    val media_type: String? = null,
    val name: String? = null,
    val original_language: String? = null,
    val original_name: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
) {
    @PrimaryKey(autoGenerate = true)
    var mainId: Int? = null
}
