package com.ger.common.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.ger.common.data.WithName

@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    list: List<WithName>,
    chooseIndex: State<Int>,
    dropdownMenuName: String,
    onChooseItem: (index: Int) -> Unit,
) {
    Column (modifier) {
        Text(
            text = dropdownMenuName,
            fontWeight = FontWeight.Bold
        )

        LazyColumn {
            list.forEachIndexed { index, name ->
                item {
                    val color = if (index == chooseIndex.value) {
                        Color.LightGray
                    } else {
                        Color.Transparent
                    }
                    Text(
                        modifier = Modifier.clickable {
                            onChooseItem(index)
                        }.background(color),
                        text = name.name
                    )
                }
            }
        }
    }

}