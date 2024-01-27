package com.roshanadke.weekwatch.presentation.screens.states

import com.roshanadke.weekwatch.domain.models.TrendingItem

data class TrendingItemListState(
    val isLoading: Boolean = false,
    val list: List<TrendingItem> = mutableListOf()
)

