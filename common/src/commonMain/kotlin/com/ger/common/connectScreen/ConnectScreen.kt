package com.ger.common.connectScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.EditNumber
import com.ger.common.customUI.EditText
import com.ger.common.customUI.FocusableButton
import com.ger.common.customUI.Header
import com.ger.common.strings.S

@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    onClick: (ip: String, port: Int) -> Unit,
    onBack: () -> Unit,
    isConnect: State<Boolean>
) {
    val ip = remember { mutableStateOf("192.168.31.63") }
    val port = remember { mutableStateOf("9105") }

    LaunchedEffect(isConnect.value) {
        if (isConnect.value) {
            onBack()
        }
    }

    Column(modifier.fillMaxSize()) {
        Header(headerText = S.strings.connect, onBack = onBack)
        Box(Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {
            Card(Modifier.align(Alignment.Center)) {
                Column() {
                    Spacer(Modifier.height(10.dp))
                    EditText(
                        value = ip,
                        label = S.strings.ip,
                    )
                    Spacer(Modifier.height(10.dp))
                    EditNumber(
                        value = port,
                        label = S.strings.port
                    )
                    Spacer(Modifier.height(10.dp))
                    FocusableButton(
                        Modifier.align(Alignment.CenterHorizontally),
                        text = S.strings.connect,
                        onClick = { onClick(ip.value, port.value.toInt()) }
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }

        }
    }
}