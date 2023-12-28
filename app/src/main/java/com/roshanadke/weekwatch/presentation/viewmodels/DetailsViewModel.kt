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
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import com.roshanadke.weekwatch.presentation.screens.TrendingItemListState
import com.roshanadke.weekwatch.presentation.screens.TvShowDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel() {

    private var _tvShowDetailsState: MutableState<TvShowDetailsState> =
        mutableStateOf(TvShowDetailsState())
    val tvShowDetailsState: State<TvShowDetailsState> = _tvShowDetailsState

    private var _similarItemListState: MutableState<TrendingItemListState> =
        mutableStateOf(TrendingItemListState())
    var similarItemListState: State<TrendingItemListState> = _similarItemListState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getTvShowDetails(id: String) {
        repository.getTvShowDetails(id).onEach {
            when (it) {
                is UiState.Error -> {
                    _tvShowDetailsState.value = _tvShowDetailsState.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            when (val message = it.message) {
                                null -> UiText.StringResource(R.string.something_went_wrong)
                                else -> UiText.DynamicString(message)
                            }
                        )
                    )
                }

                is UiState.Loading -> {
                    _tvShowDetailsState.value = _tvShowDetailsState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    _tvShowDetailsState.value = _tvShowDetailsState.value.copy(
                        isLoading = false,
                        showDetails = it.data ?: TvShowDetails()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSimilarShows(id: String) {
        repository.getSimilarShows(id).onEach { responseDtoUiState ->
            when (responseDtoUiState) {
                is UiState.Error -> {
                    _similarItemListState.value = _similarItemListState.value.copy(
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
                    _similarItemListState.value = _similarItemListState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    val list =
                        responseDtoUiState.data?.trendingItemDtoList?.map { it.toTrendingItem() }
                    _similarItemListState.value = _similarItemListState.value.copy(
                        isLoading = false,
                        list = list ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addToFavourites(item: TrendingItem?) {
        viewModelScope.launch {
            repository.addToFavourites(item)
        }
    }

    fun removeFromFavourites(id: Int?) {
        viewModelScope.launch {
            repository.removeFromFavourites(id)
        }
    }

}




