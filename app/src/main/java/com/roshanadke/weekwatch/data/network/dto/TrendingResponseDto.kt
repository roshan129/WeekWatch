package com.roshanadke.weekwatch.data.network.dto

import com.google.gson.annotations.SerializedName

data class TrendingResponseDto(
    val page: Int?,
    @SerializedName("results")
    val trendingItemDtoList: List<TrendingItemDto>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)