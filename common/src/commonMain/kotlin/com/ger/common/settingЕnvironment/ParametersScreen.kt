package com.ger.common.settingЕnvironment

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ger.common.customUI.EditNumber
import com.ger.common.customUI.FocusableButton
import com.ger.common.data.Conveyor
import com.ger.common.data.WithName
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.strings.S
import com.ger.common.utils.CustomDropdownMenu

@Composable
fun ParametersScreen(
    modifier: Modifier = Modifier,
    conveyors: List<Conveyor>,
    completedPallets: List<CompletedPallet>,
    onAddSettings: (
        conveyorIndex: Int, isConveyorSignal: Int,
        completedPalletIndex: Int, isPalletSignal: Int,
        isProgramWork: Int, palletPositionIndex: Int,
        zGap: Int
    ) -> Unit,
) {
    Column(modifier) {
        val completedPalletsIndex = remember { mutableStateOf(0) }
        val completedPallet = remember { mutableStateOf(CompletedPallet()) }
        CustomDropdownMenu(
            modifier = Modifier,
            list = completedPallets,
            chooseIndex = completedPalletsIndex,
            dropdownMenuName = "Расскладка"
        ) { index ->
            completedPallet.value = completedPallets[index]
            completedPalletsIndex.value = index
        }

        val isPalletSignal = remember { mutableStateOf("1") }
        EditNumber(
            value = isPalletSignal,
            label = "Сигнал, который указывает, что паллета на месте"
        )

        val conveyorIndex = remember { mutableStateOf(0) }
        val conveyor = remember { mutableStateOf(Conveyor()) }

        CustomDropdownMenu(
            modifier = Modifier,
            list = conveyors,
            chooseIndex = conveyorIndex,
            dropdownMenuName = S.strings.conveyorsList
        ) { index ->
            conveyor.value = conveyors[index]
            conveyorIndex.value = index
        }

        val isConveyorSignal = remember { mutableStateOf("2") }
        EditNumber(
            value = isConveyorSignal,
            label = "Сигнал, который указывает, что продукт можно забрать с конвейера"
        )

        val isProgramWork = remember { mutableStateOf("3") }
        EditNumber(
            value = isProgramWork,
            label = "Сигнал, при котором будет работать программа"
        )

        val zGap = remember { mutableStateOf("0") }
        EditNumber(
            value = zGap,
            label = "Зазор по высоте"
        )

        val palletPositionIndex = remember { mutableStateOf(0) }

        class Position(override val name: String) : WithName

        val palletPositions = remember {
            listOf(
                Position("Верхний правый угол"),
                Position("Верхний левый угол"),
                Position("Нижний левый угол"),
                Position("Нижний правый угол"),
            )
        }
        val palletPosition = remember { mutableStateOf(Position("Верхний правый угол")) }
        CustomDropdownMenu(
            modifier = Modifier,
            list = palletPositions,
            chooseIndex = palletPositionIndex,
            dropdownMenuName = "Положение паллеты"
        ) { index ->
            palletPosition.value = palletPositions[index]
            palletPositionIndex.value = index
        }

        FocusableButton(
            onClick = {
                onAddSettings(
                    conveyorIndex.value,
                    isConveyorSignal.value.toInt(),
                    completedPalletsIndex.value,
                    isPalletSignal.value.toInt(),
                    isProgramWork.value.toInt(),
                    palletPositionIndex.value,
                    zGap.value.toInt()
                )

            },
            text = S.strings.add
        )
    }
}