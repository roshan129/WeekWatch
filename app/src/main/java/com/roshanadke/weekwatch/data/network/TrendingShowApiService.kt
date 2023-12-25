package com.roshanadke.weekwatch.data.network

import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.data.network.dto.tv_details.TvShowDetailsMainDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingShowApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val BACKDROP_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
        const val POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    @GET("3/trending/tv/day")
    suspend fun getAllTrending(): TrendingResponseDto

    @GET("3/tv/{series_id}")
    suspend fun getTvShowDetails(
        @Path("series_id") id: String
    ): TvShowDetailsMainDto

    @GET("3/tv/{series_id}/similar")
    suspend fun getSimilarShows(
        @Path("series_id") id: String
    ): TrendingResponseDto

    @GET("3/search/tv")
    suspend fun fetchSearchedShows(
        @Query("query") query: String
    ): TrendingResponseDto

}