package com.ger.common.utils

import androidx.compose.runtime.MutableState

fun <T> MutableState<List<T>>.add(newValue: T) {
    val value = mutableListOf<T>()
    value.addAll(this.value)
    value.add(newValue)

    this.value = value
}

fun <T> MutableState<List<T>>.change(index: Int, newValue: T) {
    val value = mutableListOf<T>()
    value.addAll(this.value)

    value[index] = newValue

    this.value = value
}

fun <T> MutableState<List<T>>.delete(index: Int) {
    val value = mutableListOf<T>()
    value.addAll(this.value)
    value.removeAt(index)

    this.value = value
}