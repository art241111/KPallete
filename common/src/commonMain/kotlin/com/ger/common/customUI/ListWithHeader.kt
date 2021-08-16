package com.ger.common.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ListWithHeader(
    modifier: Modifier = Modifier,
    headerText: String,
    content: LazyListScope.() -> Unit,
) {
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            text = headerText
        )

        HorizontalSeparator(height = 1.dp)

        LazyColumn(reverseLayout = true) {
            content()
        }
    }
}

@Composable
fun <T> ListWithHeaderAndCloseButton(
    modifier: Modifier = Modifier,
    headerText: String,
    contentArray: List<T>,
    onDelete: (index: Int) -> Unit,
    onCheck: (index: Int) -> Unit,
    content: @Composable() (LazyItemScope.(index: Int, item: T) -> Unit),
) {
    ListWithHeader(
        modifier = modifier,
        headerText = headerText
    ) {
        itemsIndexed(contentArray) { index, _content ->
            Box(
                Modifier.clickable {
                    onCheck(index)
                }
            ) {
                Column {
                    content(index, _content)
                    HorizontalSeparator()
                }

                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { onDelete(index) }
                ) {
                    Icon(Icons.Default.Close, "x")
                }
            }
        }
    }
}
