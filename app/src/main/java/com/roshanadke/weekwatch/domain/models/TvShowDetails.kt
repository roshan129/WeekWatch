package com.roshanadke.weekwatch.domain.models

import com.roshanadke.weekwatch.data.network.dto.tv_details.SeasonDto

data class TvShowDetails(
    val id: Int = 0,
    val numberOfSeasons: Int = 0,
    val seasons: List<Season> = mutableListOf(),
)