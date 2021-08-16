package com.ger.common.strings

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale

object GetStrings {
    fun get(locale: Locale): Strings {
        return when (locale.language) {
            "ru" -> {
                RusStrings()
            }
            "en" -> {
                EnStrings()
            }
            else -> EnStrings()
        }
    }
}

object S {
    val strings: Strings
        @Composable
        get() = GetStrings.get(Locale.current)
}
