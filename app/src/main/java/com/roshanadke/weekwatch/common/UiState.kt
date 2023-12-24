package com.roshanadke.weekwatch.common



sealed class UiState<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): UiState<T>(data)
    class Success<T>(data: T? = null): UiState<T>(data)
    class Error<T>(data: T? = null, message: String? = null): UiState<T>(data, message)
}

