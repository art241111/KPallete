package com.ger.common.customUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun Spinner(
    list: List<String>,
    selectIndex: MutableState<Int> = mutableStateOf(0),
    isEnabled: Boolean,
    onSelect: (Int) -> Unit,
) {
    val isShow = remember { mutableStateOf(false)}
    Column {
        Button(
            onClick = {isShow.value = !isShow.value},
            enabled = isEnabled
        ) {
            Text(if (list.isNotEmpty()) list[selectIndex.value] else "")
        }

        if(isShow.value) {
            LazyColumn {
                itemsIndexed(list) { index, item ->
                    Button(onClick = {
                        selectIndex.value = index
                        isShow.value = false
                        onSelect(index)
                    }){
                        Text(item)
                    }
                }
            }
        }
    }
}

