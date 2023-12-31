package com.roshanadke.weekwatch.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.weekwatch.R
import com.roshanadke.weekwatch.common.UiEvent
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.common.UiText
import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import com.roshanadke.weekwatch.domain.use_case.TrendingShowUseCaseState
import com.roshanadke.weekwatch.presentation.screens.TrendingItemListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class TrendingShowViewModel @Inject constructor(
    private val useCaseState: TrendingShowUseCaseState
) : ViewModel() {

    private var _trendingItemListState: MutableState<TrendingItemListState> =
        mutableStateOf(TrendingItemListState())
    var trendingItemListState: State<TrendingItemListState> = _trendingItemListState

    private var _searchListState: MutableState<TrendingItemListState> =
        mutableStateOf(TrendingItemListState())
    var searchListState: State<TrendingItemListState> = _searchListState

    private var _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var _favouritesList: MutableState<List<TrendingDataEntity>> =
        mutableStateOf(emptyList())
    private val favouritesList: State<List<TrendingDataEntity>> = _favouritesList

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllTrendingShows()
        viewModelScope.launch {
            _searchQuery.debounce(500).collectLatest {
                if (it.isNotEmpty()) {
                    fetchSearchedShows(it)
                }
            }
        }
        viewModelScope.launch {
            useCaseState.getFavouriteUseCase().collectLatest {
                _favouritesList.value = it
                refreshTrendingList()
            }
        }
    }

    private fun refreshTrendingList() {
        if (trendingItemListState.value.list.isNotEmpty()) {
            val favouriteIdList = favouritesList.value.map { it.id }

            val trendingList = trendingItemListState.value.list.map { item ->
                item.apply {
                    isFavourite = favouriteIdList.contains(id)
                }
            }
            _trendingItemListState.value = _trendingItemListState.value.copy(
                list = trendingList
            )
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun getAllTrendingShows() {
        useCaseState.trendingShowUseCase().onEach { responseDtoUiState ->
            when (responseDtoUiState) {
                is UiState.Error -> {
                    _trendingItemListState.value = _trendingItemListState.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            when (val message = responseDtoUiState.message) {
                                null -> UiText.StringResource(R.string.something_went_wrong)
                                else -> UiText.DynamicString(message)
                            }
                        )
                    )

                }

                is UiState.Loading -> {
                    _trendingItemListState.value = _trendingItemListState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    val favouriteIdList = favouritesList.value.map { it.id }

                    val trendingList = responseDtoUiState.data?.trendingItemDtoList?.map { item ->
                        item.toTrendingItem().apply {
                            isFavourite = favouriteIdList.contains(id)
                        }
                    }

                    _trendingItemListState.value = _trendingItemListState.value.copy(
                        list = trendingList ?: emptyList(),
                        isLoading = false
                    )
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun fetchSearchedShows(query: String) {
        useCaseState.searchedShowUseCase(query).onEach { responseDtoUiState ->
            when (responseDtoUiState) {
                is UiState.Error -> {
                    _searchListState.value = _searchListState.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            when (val message = responseDtoUiState.message) {
                                null -> UiText.StringResource(R.string.something_went_wrong)
                                else -> UiText.DynamicString(message)
                            }
                        )
                    )
                }

                is UiState.Loading -> {
                    _searchListState.value = _searchListState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    val searchList = responseDtoUiState.data?.trendingItemDtoList?.map { it.toTrendingItem() }
                    _searchListState.value = _searchListState.value.copy(
                        list = searchList ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}