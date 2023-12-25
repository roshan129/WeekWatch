package com.roshanadke.weekwatch.common

sealed class UiEvent {
    data class ShowSnackbar(val message: UiText): UiEvent()
}