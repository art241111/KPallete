package com.ger.common.areaScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.EditText
import com.ger.common.customUI.FocusableButton
import com.ger.common.customUI.Header
import com.ger.common.data.Area
import com.ger.common.data.Point
import com.ger.common.strings.S
import com.ger.common.utils.ListWithHeaderAndCloseButton
import com.ger.common.utils.VerticalSeparator

@Composable
fun AreaScreen(
    modifier: Modifier = Modifier,
    onAddArea: (area: Area) -> Unit,
    onBack: () -> Unit,
    areas: State<List<Area>>,
    onDelete: (index: Int) -> Unit,
    point: State<Point>,
    onAddPoint: (index: Int) -> Unit,
    changePointIndex: State<Int>,
    editArea: Area,
    onCheck: (index: Int) -> Unit,
    editAreaIndex: MutableState<Int>
) {
    Column(modifier) {
        Header(
            onBack = onBack,
            headerText = S.strings.addArea
        )

        Row {
            AreaList(
                modifier = Modifier.width(300.dp),
                areas = areas,
                onDelete = onDelete,
                onCheck = onCheck
            )
            VerticalSeparator(width = 1.dp)
            AddNewArea(modifier = Modifier.weight(1f), onAddArea, point, onAddPoint, changePointIndex, editArea, editAreaIndex)
        }
    }
}

@Composable
private fun AreaList(
    modifier: Modifier = Modifier,
    areas: State<List<Area>>,
    onDelete: (index: Int) -> Unit,
    onCheck: (index: Int) -> Unit
) {
    ListWithHeaderAndCloseButton(
        modifier = modifier,
        headerText = S.strings.areasList,
        contentArray = areas.value,
        onDelete = onDelete,
        onCheck = onCheck
    ) { _, area ->
        Column(modifier = Modifier.padding(10.dp)) {
            Text("${S.strings.name}: ${area.name}")
            Text("${S.strings.name}: ${area.leftTopPosition}")
            Text("${S.strings.name}: ${area.rightTopPosition}")
            Text("${S.strings.name}: ${area.leftBottomPosition}")
        }
    }
}

@Composable
private fun AddNewArea(
    modifier: Modifier = Modifier,
    onAddArea: (area: Area) -> Unit,
    point: State<Point>,
    addPoint: (index: Int) -> Unit,
    changePointIndex: State<Int> = mutableStateOf(-1),
    area: Area,
    editAreaIndex: MutableState<Int>
) {
    val areaName = remember (area.name) { mutableStateOf(area.name) }

    val changePoint = remember(changePointIndex.value) { mutableStateOf(changePointIndex.value) }

    LaunchedEffect(point.value) {
        when (changePoint.value) {
            0 -> {
                area.leftTopPosition = point.value
                changePoint.value = -1
            }
            1 -> {
                area.rightTopPosition = point.value
                changePoint.value = -1
            }
            2 -> {
                area.leftBottomPosition = point.value
                changePoint.value = -1
            }
        }
    }

    Column(modifier) {
        Header(headerText = S.strings.addArea)

        EditText(
            value = areaName,
            label = S.strings.areaName,
        )

        AreaPoints(
            area = area,
            onAddTopStartPoint = {
                area.name = areaName.value
                addPoint(0)
            },
            onAddTopEndPoint = {
                area.name = areaName.value
                addPoint(1)
            },
            onAddBottomStartPoint = {
                area.name = areaName.value
                addPoint(2)
            },
        )

        FocusableButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onAddArea(area.copy(name = areaName.value))
                area.name = ""
                areaName.value = ""
            },
            text = if(editAreaIndex.value == -1) S.strings.add else S.strings.edit
        )
    }
}

@Composable
private fun AreaPoints(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    onAddTopStartPoint: () -> Unit,
    onAddTopEndPoint: () -> Unit,
    onAddBottomStartPoint: () -> Unit,
    area: Area
) {
    val isAddTopStartPoint = remember(area) {
        if (area.leftTopPosition == Point()) mutableStateOf(false) else mutableStateOf(true)
    }
    val isAddTopEndPoint = remember(area) {
        if (area.rightTopPosition == Point()) mutableStateOf(false) else mutableStateOf(true)
    }
    val isAddBottomStartPoint = remember(area) {
        if (area.leftBottomPosition == Point()) mutableStateOf(false) else mutableStateOf(true)
    }

    Box(modifier = modifier.size(width = 270.dp, height = 170.dp)) {
        Box(modifier = Modifier.size(width = 200.dp, height = 100.dp).align(Alignment.Center).background(color))

        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = {
                onAddTopStartPoint()
            }
        ) {
            AddButton(isAddTopStartPoint.value)
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = {
                onAddTopEndPoint()
            }
        ) {
            AddButton(isAddTopEndPoint.value)
        }

        IconButton(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = {
                onAddBottomStartPoint()
            }
        ) {
            AddButton(isAddBottomStartPoint.value)
        }
    }
}

@Composable
private fun AddButton(
    status: Boolean
) {
    if (!status) {
        Icon(Icons.Default.AddCircle, "+", tint = Color.Red)
    } else {
        Icon(Icons.Default.Done, "+", tint = Color.Green)
    }
}
