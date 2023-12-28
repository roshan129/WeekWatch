package com.roshanadke.weekwatch.common

sealed class UiEvent {
    data class ShowSnackBar(val message: UiText): UiEvent()
}