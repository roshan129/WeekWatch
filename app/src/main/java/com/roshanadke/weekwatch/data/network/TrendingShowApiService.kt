package com.roshanadke.weekwatch.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingShowApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
    }

    @GET("3/trending/all/day")
    fun getAllTrending()

}