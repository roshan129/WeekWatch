package com.roshanadke.weekwatch.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.weekwatch.BuildConfig
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.network.dto.TrendingItemDto
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.repository.TrendingShowRepository
import com.roshanadke.weekwatch.presentation.screens.TrendingItemListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class TrendingShowViewModel @Inject constructor(
    private val repository: TrendingShowRepository
) : ViewModel() {

    private var _trendingList: MutableState<List<TrendingItem>> = mutableStateOf(emptyList())
    val trendingList: State<List<TrendingItem>> = _trendingList

    private var _trendingItemListState: MutableState<TrendingItemListState> =
        mutableStateOf(TrendingItemListState())
    var trendingItemListState: State<TrendingItemListState> = _trendingItemListState

    private var _searchListState: MutableState<TrendingItemListState> =
        mutableStateOf(TrendingItemListState())
    var searchListState: State<TrendingItemListState> = _searchListState

    private var _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String>  = _searchQuery

    init {
        getAllTrendingShows()

        viewModelScope.launch {
            _searchQuery.debounce(500).collectLatest {
                if(!it.isEmpty()) {
                    fetchSearchedShows(it)
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun getAllTrendingShows() {
        repository.getAllTrendingShows().onEach {
            when (it) {
                is UiState.Error -> {
                    _trendingItemListState.value = _trendingItemListState.value.copy(
                        isLoading = false
                    )
                }

                is UiState.Loading -> {
                    _trendingItemListState.value = _trendingItemListState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    val trendingList = it.data?.trendingItemDtoList?.map { it.toTrendingItem() }
                    _trendingItemListState.value = _trendingItemListState.value.copy(
                        list = trendingList ?: emptyList(),
                        isLoading = false
                    )
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun fetchSearchedShows(query: String) {
        repository.fetchSearchedShows(query).onEach {
            when(it) {
                is UiState.Error -> {
                    _searchListState.value = _searchListState.value.copy(
                        isLoading = false
                    )
                }
                is UiState.Loading -> {
                    _searchListState.value = _searchListState.value.copy(
                        isLoading = true
                    )
                }
                is UiState.Success -> {
                    val searchList = it.data?.trendingItemDtoList?.map { it.toTrendingItem() }
                    _searchListState.value = _searchListState.value.copy(
                        list = searchList ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}