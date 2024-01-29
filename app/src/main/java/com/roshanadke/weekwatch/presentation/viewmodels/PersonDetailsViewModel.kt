package com.roshanadke.weekwatch.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.weekwatch.R
import com.roshanadke.weekwatch.common.UiEvent
import com.roshanadke.weekwatch.common.UiState
import com.roshanadke.weekwatch.common.UiText
import com.roshanadke.weekwatch.domain.models.people.PersonDetailsModel
import com.roshanadke.weekwatch.domain.repository.PersonDetailsRepository
import com.roshanadke.weekwatch.presentation.screens.states.PersonDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val repository: PersonDetailsRepository
) : ViewModel() {

    private var _personDetailsState = mutableStateOf(PersonDetailsState())
    val personDetailsState: State<PersonDetailsState?> = _personDetailsState

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = MutableSharedFlow<UiEvent>()

    fun getPersonDetails(id: Int) {
        repository.getPersonDetails(id).onEach {
            when (it) {
                is UiState.Error -> {
                    _personDetailsState.value = _personDetailsState.value.copy(
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
                    _personDetailsState.value = _personDetailsState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Success -> {
                    _personDetailsState.value = _personDetailsState.value.copy(
                        isLoading = false,
                        personDetails = it.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}