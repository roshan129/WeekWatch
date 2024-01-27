package com.roshanadke.weekwatch.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.weekwatch.R
import com.roshanadke.weekwatch.common.UiEvent
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.common.UiText
import com.roshanadke.weekwatch.domain.repository.PeopleRepository
import com.roshanadke.weekwatch.presentation.screens.states.PeopleListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repository: PeopleRepository
) : ViewModel() {

    private var _peopleListUiState = mutableStateOf(PeopleListUiState())
    val peopleListUiState: State<PeopleListUiState> = _peopleListUiState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getPopularPeopleList()
    }

    fun getPopularPeopleList() {
        Timber.d("result: api called")
        repository.getPopularPeopleList(1).onEach {
            when (it) {
                is UiState.Error -> {
                    Timber.d("error: ${it.message}")
                    _peopleListUiState.value = _peopleListUiState.value.copy(
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
                    _peopleListUiState.value = _peopleListUiState.value.copy(isLoading = true)
                }

                is UiState.Success -> {
                    Timber.d("results: ${it.data?.personDtoList?.size}")
                    _peopleListUiState.value = _peopleListUiState.value.copy(
                        isLoading = false,
                        peopleListMainDto = it.data
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

}