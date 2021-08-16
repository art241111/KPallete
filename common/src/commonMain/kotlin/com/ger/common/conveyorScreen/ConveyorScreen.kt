package com.ger.common.conveyorScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.EditText
import com.ger.common.customUI.FocusableButton
import com.ger.common.customUI.Header
import com.ger.common.data.Conveyor
import com.ger.common.data.Point
import com.ger.common.strings.S
import com.ger.common.utils.ListWithHeaderAndCloseButton
import com.ger.common.utils.VerticalSeparator

@Composable
fun ConveyorScreen(
    modifier: Modifier = Modifier,
    onAddConveyor: (Conveyor) -> Unit,
    conveyors: State<List<Conveyor>>,
    onBack: () -> Unit,
    onDelete: (index: Int) -> Unit,
    point: State<Point>,
    addPoint: () -> Unit,
    editConveyor: Conveyor,
    onCheck: (index: Int) -> Unit,
    editConveyorIndex: MutableState<Int>
) {

    Column(modifier) {
        Header(
            onBack = onBack,
            headerText = S.strings.addConveyor
        )

        Row {
            ConveyorList(
                modifier = Modifier.width(300.dp),
                conveyors = conveyors,
                onDelete = onDelete,
                onCheck = onCheck
            )

            VerticalSeparator(width = 1.dp)

            AddNewConveyor(
                modifier = Modifier.weight(1f),
                onAddConveyor = onAddConveyor,
                point = point,
                addPoint = addPoint,
                editConveyor = editConveyor,
                editConveyorIndex = editConveyorIndex
            )
        }
    }
}

@Composable
private fun AddNewConveyor(
    modifier: Modifier = Modifier,
    onAddConveyor: (Conveyor) -> Unit,
    point: State<Point>,
    addPoint: () -> Unit,
    editConveyor: Conveyor,
    editConveyorIndex: MutableState<Int>
) {
    val conveyorName = remember(editConveyor.name) { mutableStateOf(editConveyor.name) }

    Column(modifier) {
        Header(headerText = S.strings.addConveyor)

        EditText(
            value = conveyorName,
            label = S.strings.conveyorName,
        )

        Spacer(Modifier.height(5.dp))
        FocusableButton(
            text = S.strings.addPoint,
            onClick = {
                addPoint()
                editConveyor.name = conveyorName.value
            }
        )

        FocusableButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onAddConveyor(
                    Conveyor(
                        name = conveyorName.value,
                        takePosition = if (point.value == Point()) editConveyor.takePosition else point.value
                    )
                )
            },
            text = if (editConveyorIndex.value == -1) S.strings.add else S.strings.edit,
        )

    }
}

@Composable
private fun ConveyorList(
    modifier: Modifier = Modifier,
    conveyors: State<List<Conveyor>>,
    onDelete: (index: Int) -> Unit,
    onCheck: (index: Int) -> Unit,
) {
    ListWithHeaderAndCloseButton(
        modifier = modifier,
        headerText = S.strings.conveyorsList,
        contentArray = conveyors.value,
        onDelete = onDelete,
        onCheck = onCheck
    )
    { _, conveyor ->
        Column(modifier = Modifier.padding(10.dp)) {
            Text("${S.strings.name}: ${conveyor.name}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.position}: ${conveyor.takePosition}")
        }
    }

}

