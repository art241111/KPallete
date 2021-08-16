package com.ger.common.mainListScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.Header
import com.ger.common.nav.Navigation
import com.ger.common.nav.Screens
import com.ger.common.strings.S

@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    navigation: Navigation,
    onConnect: () -> Unit,
    isConnect: State<Boolean>,
) {
    val focusList = remember { List(6) { FocusRequester() } }

    Column(modifier.fillMaxSize()) {
        Header(headerText = S.strings.mainListScreen)

        Box(Modifier.fillMaxSize()) {
            Card(Modifier.align(Alignment.Center)) {

                LazyColumn(
                    contentPadding = PaddingValues(15.dp)
                ) {
                    item {
                        ListCard(
                            modifier = Modifier.focusOrder(focusList[0]) {
                                previous = focusList[2]
                                next = focusList[1]
                            },
                            text = "1. Добавить паллету",
                            onClick = {
                                navigation.moveToScreen(Screens.ADD_PALLET)
                            }
                        )
                    }

                    item {
                        ListCard(
                            modifier = Modifier.focusOrder(focusList[1]) {
                                previous = focusList[0]
                                next = focusList[2]
                            },
                            text = "2. Добавить продукт",
                            onClick = {
                                navigation.moveToScreen(Screens.ADD_PRODUCT)
                            }
                        )
                    }

                    item {
                        ListCard(
                            modifier = Modifier.focusOrder(focusList[2]) {
                                previous = focusList[1]
                                next = focusList[3]
                            },
                            text = "3. Создать раскладку",
                            onClick = {
                                navigation.moveToScreen(Screens.CREATE_LAYOUT)
                            }
                        )
                    }

                    item {
                        ListCard(
                            modifier = Modifier.focusOrder(focusList[3]) {
                                previous = focusList[2]
                                next = focusList[4]
                            },
                            text = "4. Запомнить положение конвеера ",
                            onClick = {
                                navigation.moveToScreen(Screens.ADD_CONVEYOR)
                            }
                        )
                    }

                    item {
                        ListCard(
                            modifier = Modifier.focusOrder(focusList[4]) {
                                previous = focusList[3]
                                next = focusList[5]
                            },
                            text = "5. Создание зоны разгрузки",
                            onClick = {
                                navigation.moveToScreen(Screens.ADD_AREA)
                            }
                        )
                    }

                    item {
                        ListCard(
                            modifier = Modifier.focusOrder(focusList[5]) {
                                previous = focusList[4]
                                next = focusList[0]
                            },
                            text = "6. Настройка окружения",
                            onClick = {
                                navigation.moveToScreen(Screens.SETTING_ENVIRONMENT)
                            }
                        )
                    }
                }
            }

            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd).padding(15.dp),
                onClick = onConnect
            ) {
                if (!isConnect.value) {
                    Icon(Icons.Default.AddCircle, "Connect", tint = Color.Red, modifier = Modifier.size(40.dp))
                } else {
                    Icon(Icons.Default.Done, "Connect", tint = Color.Green, modifier = Modifier.size(40.dp))
                }

            }


        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ListCard(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val colors: Color = if (interactionSource.collectIsFocusedAsState().value) {
        Color.Red
    } else {
        Color.Transparent
    }


    Row(
        modifier.clickable { onClick() }
            .onPreviewKeyEvent {
                if (
                    it.key == Key.Enter ||
                    it.key == Key.Spacebar
                ) {
                    onClick()
                }
                false
            }
            .padding(vertical = 10.dp)
            .border(1.dp, colors)
            .focusable(interactionSource = interactionSource),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6
        )
    }
}