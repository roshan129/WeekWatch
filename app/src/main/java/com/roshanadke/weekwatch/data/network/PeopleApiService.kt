package com.roshanadke.weekwatch.data.network

import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.data.network.dto.people.PeopleListMainDto
import com.roshanadke.weekwatch.data.network.dto.people.PersonDetailsDto
import com.roshanadke.weekwatch.data.network.dto.tv_details.TvShowDetailsMainDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApiService {


    @GET("3/trending/tv/day")
    suspend fun getAllTrending(): TrendingResponseDto

    @GET("3/person/popular")
    suspend fun getPopularPersonsList(
        @Query("page") page: Int
    ): PeopleListMainDto

    @GET("3/person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") id: Int
    ): PersonDetailsDto

}