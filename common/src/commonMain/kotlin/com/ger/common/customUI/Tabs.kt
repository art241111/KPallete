package com.ger.common.customUI

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction

@Composable
fun TabList(
    modifier: Modifier = Modifier,
    nameList: List<String>,
    onAdd: () -> Unit,
    onEditName: (index: Int, newName: String) -> Unit,
    onCheck: (index: Int) -> Unit
) {
    val checkIndex = remember { mutableStateOf(0) }
    Row(modifier.fillMaxWidth()) {
        nameList.forEachIndexed { index, name ->
            Tabs(
                text = name,
                onEditText = { newName -> onEditName(index, newName) },
                onClick = {
                    onCheck(index)
                    checkIndex.value = index
                },
                isFocused = checkIndex.value == index
            )
        }
        Tabs(
            text = "+",
            onEditText = null,
            onClick = { onAdd() }
        )
    }

}

@OptIn(ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    text: String,
    isFocused: Boolean = false,
    onEditText: ((newText: String) -> Unit)?,
    onClick: () -> Unit,
) {
    val name = remember { mutableStateOf(text) }
    val isEdit = remember { mutableStateOf(false) }
    Box(
        modifier.combinedClickable(
            onClick = onClick,
            onDoubleClick = { if (onEditText != null) isEdit.value = true }
        )
            .background(if (isFocused) Color.Gray else Color.White)
    ) {
        if (!isEdit.value) {
            Text(text)
        } else {
            TextField(
                modifier = modifier
                    .onKeyEvent {
                        if (it.key == Key.Enter) {
                            if (onEditText != null) {
                                onEditText(name.value)
                            }
                            isEdit.value = false
                            true //true -> consumed
                        } else false
                    },
                value = name.value,
                onValueChange = {
                    name.value = it
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (onEditText != null) {
                            onEditText(name.value)
                        }
                        isEdit.value = false
                    }
                )
            )
        }
    }
}
