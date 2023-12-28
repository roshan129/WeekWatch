package com.roshanadke.weekwatch.data.repository

import com.roshanadke.weekwatch.data.network.TrendingShowApiService
import com.roshanadke.weekwatch.data.network.dto.TrendingResponseDto
import com.roshanadke.weekwatch.data.network.dto.tv_details.TvShowDetailsMainDto
import com.roshanadke.weekwatch.data.utils.getTrendingItemDto
import com.roshanadke.weekwatch.data.utils.getTrendingResponseDto
import com.roshanadke.weekwatch.data.utils.getTvShowDetailsMainDto

class TrendingShowApiServiceFake : TrendingShowApiService {

    var errorToReturn: Exception? = null
    private var trendingItemDtoList =
        listOf(
            getTrendingItemDto(123, "Breaking Bad"),
            getTrendingItemDto(124, "Breaking In"),
            getTrendingItemDto(125, "Doctor Who")
        )
    var trendingResponseDto = getTrendingResponseDto(trendingItemDtoList)
    private var tvShowDetailsMainDto = getTvShowDetailsMainDto(id = 1234)

    override suspend fun getAllTrending(): TrendingResponseDto {
        return if (errorToReturn != null) {
            throw errorToReturn!!
        } else trendingResponseDto
    }

    override suspend fun getTvShowDetails(id: String): TvShowDetailsMainDto {
        if(errorToReturn != null) throw errorToReturn!!
        else return tvShowDetailsMainDto
    }

    override suspend fun getSimilarShows(id: String): TrendingResponseDto {
        if(errorToReturn != null) throw errorToReturn!!
        else return trendingResponseDto
    }

    override suspend fun fetchSearchedShows(query: String): TrendingResponseDto {
        if (errorToReturn != null) {
            throw errorToReturn!!
        }
        return trendingResponseDto.copy(trendingItemDtoList = trendingItemDtoList.filter {
            it.title?.contains(
                query, ignoreCase = true
            ) == true
        })
    }

}