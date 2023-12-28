package com.roshanadke.weekwatch.data.network.dto.tv_details

import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.google.gson.annotations.SerializedName

data class TvShowDetailsMainDto(
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("episode_run_time")
    val episodeRunTimeList: List<Any>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    val homepage: String,
    val id: Int,
    @SerializedName("in_production")
    val inProduction: Boolean,
    @SerializedName("languages")
    val languageList: List<String>,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    val name: String,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("origin_country")
    val originCountryList: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("seasons")
    val seasonList: List<SeasonDto>,
    val status: String,
    val tagline: String,
    val type: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    fun toTvShowDetails(): TvShowDetails {
        return TvShowDetails(
            id = id,
            numberOfSeasons = numberOfSeasons,
            seasons = seasonList.map { it.toSeason() }
        )
    }
}