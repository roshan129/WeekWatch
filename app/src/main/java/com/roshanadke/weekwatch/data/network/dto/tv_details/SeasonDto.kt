package com.roshanadke.weekwatch.data.network.dto.tv_details

import com.roshanadke.weekwatch.domain.models.Season

data class SeasonDto(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int,
    val vote_average: Double
) {
    fun toSeason(): Season {
        return Season(
            id = id,
            episode_count = episode_count,
            season_number = season_number
        )
    }
}