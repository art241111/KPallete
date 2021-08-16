package com.ger.common.utils

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Dp.toInt(density: Density): Int = (this.value * density.density).toInt()

fun Dp.toFloat(density: Density): Float = this.value * density.density