package com.roshanadke.weekwatch.data.network

import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingShowApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
    }

    @GET("3/trending/all/day")
    suspend fun getAllTrending(
        @Query("api_key") apiKey: String
    ): TrendingResponseDto

}