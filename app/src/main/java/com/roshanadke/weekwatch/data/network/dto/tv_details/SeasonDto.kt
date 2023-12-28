package com.roshanadke.weekwatch.data.network.dto.tv_details

import com.google.gson.annotations.SerializedName
import com.roshanadke.weekwatch.domain.models.Season


data class SeasonDto(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("vote_average")
    val voteAverage: Double
) {
    fun toSeason(): Season {
        return Season(
            id = id,
            episodeCount = episodeCount,
            seasonNumber = seasonNumber
        )
    }
}