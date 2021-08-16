package com.ger.common.customUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ger.common.utils.HorizontalSeparator

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    headerText: String
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, "Back")
        }

        Text(
            modifier = Modifier.weight(1f),
            text = headerText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            fontSize = 16.sp
        )
    }
    HorizontalSeparator(height = 1.dp)
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    headerText: String
) {
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            text = headerText
        )

        HorizontalSeparator(height = 1.dp)
    }
}
