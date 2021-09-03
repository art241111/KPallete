package com.ger.common.utils

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Int.toGraphicInt(density: Density): Int = (this * density.density).toInt()
fun Int.toOriginalInt(density: Density): Int = (this / density.density).toInt()
fun Dp.toGraphicInt(density: Density): Int = (this.value * density.density).toInt()