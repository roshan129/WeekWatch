package com.roshanadke.weekwatch.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(
        val id: Int,
        val args: List<Any> = emptyList()
    ): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(
                id,
                *args.toTypedArray()
            )
        }
    }
}
