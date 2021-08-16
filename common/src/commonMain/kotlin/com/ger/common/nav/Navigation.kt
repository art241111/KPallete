package com.ger.common.nav

import androidx.compose.runtime.State

interface Navigation {
    val state: State<Screens>
    fun moveToScreen(screen: Screens)
    fun back()
}
