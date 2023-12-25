package com.roshanadke.weekwatch.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.data.local.TrendingDataEntity
import com.roshanadke.weekwatch.data.local.TvShowDao
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.domain.models.TvShowDetails
import com.roshanadke.weekwatch.domain.repository.DetailsRepository
import com.roshanadke.weekwatch.presentation.screens.TrendingItemListState
import com.roshanadke.weekwatch.presentation.screens.TvShowDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    private val dao: TvShowDao
) : ViewModel() {

    private var _tvShowDetailsState: MutableState<TvShowDetailsState> = mutableStateOf(TvShowDetailsState())
    val tvShowDetailsState: State<TvShowDetailsState> = _tvShowDetailsState

    private var _similarItemListState: MutableState<TrendingItemListState> =
        mutableStateOf(TrendingItemListState())
    var similarItemListState: State<TrendingItemListState> = _similarItemListState

    fun getTvShowDetails(id: String) {
        repository.getTvShowDetails(id).onEach {
            Timber.d("getTvShowDetails: number_of_seasons:  ${it.data?.number_of_seasons} ")

            when (it) {
                is UiState.Error -> {
                    _tvShowDetailsState.value = _tvShowDetailsState.value.copy(
                        isLoading = false
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
        repository.getSimilarShows(id).onEach {
            Timber.d("getSimilarShows: list size: ${it.data?.trendingItemDtoList?.size}")

            when (it) {
                is UiState.Error -> {
                    _similarItemListState.value = _similarItemListState.value.copy(
                        isLoading = false
                    )
                }

                is UiState.Loading -> {
                    _similarItemListState.value = _similarItemListState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    val list = it.data?.trendingItemDtoList?.map { it.toTrendingItem() }
                    _similarItemListState.value = _similarItemListState.value.copy(
                        isLoading = false,
                        list = list ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addToFavourites(item: TrendingItem?) {
        item?.let {
            val entity = item.toTrendingDataEntity()
            viewModelScope.launch {
                dao.insert(entity)
            }
        }
    }

    fun removeFromFavourites(id: Int?) {
        id?.let {
            viewModelScope.launch {
                dao.delete(id)
            }
        }
    }

}




