package com.ger.common.settingEnvironment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.Header
import com.ger.common.data.Area
import com.ger.common.data.Conveyor
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.utils.toInt

@Composable
fun SettingEnvironmentScreen(
    modifier: Modifier = Modifier,
    conveyors: List<Conveyor>,
    areas: List<Area>,
    completedPallets: List<CompletedPallet>,
    onAddSettings: (areaIndex: Int, conveyorIndex: Int, isConveyorSignal: Int, completedPalletIndex: Int, isPalletSignal: Int, isProgramWork: Int, palletPositionIndex: Int, zGap: Int) -> Unit,
    onRun: () -> Unit,
    onBack: () -> Unit,
    onStop: () -> Unit,
) {
    Column(modifier) {
        Header(
            onBack = onBack,
            headerText = "Настройка окружения"
        )

        val areaEdit = remember { mutableStateOf(-1) }
        val scale = 0.25
        val density = LocalDensity.current

        val minX = remember(conveyors, areas) {
            var minX = 10000
            conveyors.forEach {
                if (it.takePosition.x < minX) minX = it.takePosition.x.toInt()
            }

            areas.forEach {
                if (it.leftTopPosition.x < minX) minX = it.leftTopPosition.x.toInt()
            }
            return@remember minX
        }

        val minY = remember(conveyors, areas) {
            var minY = 10000
            conveyors.forEach {
                if (it.takePosition.y < minY) minY = it.takePosition.y.toInt()
            }

            areas.forEach {
                if (it.leftTopPosition.y < minY) minY = it.leftTopPosition.y.toInt()
            }
            return@remember minY
        }

        val center = IntOffset((kotlin.math.abs(minX).dp.toInt(density)), (kotlin.math.abs(minY).dp.toInt(density)))
        Box(modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier.offset {
                    center
                },
                imageVector = Icons.Default.Close,
                contentDescription = "robot"
            )

            areas.forEachIndexed { index, area ->
                Box(
                    modifier = Modifier.offset {
                        IntOffset(
                            (((center.x + (area.leftTopPosition.x * scale).dp.toInt(density))).toInt()),
                            (((center.y - (area.leftTopPosition.y * scale).dp.toInt(density))).toInt()),
                        )
                    }
                        .size(
                            width = (kotlin.math.abs(area.leftTopPosition.x - area.rightTopPosition.x) * scale).dp,
                            height = (kotlin.math.abs(area.leftBottomPosition.y - area.leftTopPosition.y) * scale).dp
                        ).background(Color.Gray)
                        .clickable { areaEdit.value = index }
                )
            }

            conveyors.forEach { conveyor ->
                Text(
                    modifier = Modifier.offset {
                        IntOffset(
                            (((center.x + (conveyor.takePosition.x * scale).dp.toInt(density))).toInt()),
                            (((center.y - (conveyor.takePosition.y * scale).dp.toInt(density))).toInt()),
                        )
                    }

                        .background(Color.Blue),
                    text = conveyor.name,
                    color = Color.White
                )
            }

            if (areaEdit.value != -1) {
                ParametersScreen(
                    modifier = Modifier.align(Alignment.TopEnd),
                    completedPallets = completedPallets,
                    conveyors = conveyors,
                    onAddSettings = { conveyorIndex: Int, isConveyorSignal: Int,
                                      completedPalletIndex: Int, isPalletSignal: Int,
                                      isProgramWork: Int, palletPositionIndex: Int, zGap: Int ->

                        onAddSettings(
                            areaEdit.value, conveyorIndex, isConveyorSignal, completedPalletIndex,
                            isPalletSignal, isProgramWork, palletPositionIndex, zGap
                        )

                        areaEdit.value = -1
                    }
                )
            }

            ControlPanel(
                modifier = Modifier.align(Alignment.BottomStart).padding(15.dp),
                onRun = onRun,
                onStop = onStop,
            )

        }
    }
}