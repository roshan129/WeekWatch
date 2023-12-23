package com.roshanadke.weekwatch.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.weekwatch.BuildConfig
import com.roshanadke.weekwatch.data.network.dto.TrendingItemDto
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrendingShowViewModel @Inject constructor(
    private val repository: TrendingShowRepository
): ViewModel() {

    private var _trendingList: MutableState<List<TrendingItem>> = mutableStateOf(emptyList())
    val trendingList: State<List<TrendingItem>> = _trendingList

    fun getAllTrendingShows() {
        repository.getAllTrendingShows(BuildConfig.API_KEY).onEach {
            val trendingList = it.trendingItemDtoList?.map { it.toTrendingItem() }
            _trendingList.value = trendingList ?: emptyList()

        }.launchIn(viewModelScope)
    }

}