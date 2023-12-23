package com.roshanadke.weekwatch.data.network.dto

import com.google.gson.annotations.SerializedName

data class TrendingResponseDto(
    val page: Int?,
    @SerializedName("results")
    val trendingItemDtoList: List<TrendingItemDto>?,
    val total_pages: Int?,
    val total_results: Int?
)